package kg.arbocdi.builder.cfg.axon.commandSaga;

import kg.arbocdi.builder.cfg.domain.BuilderCommand;
import kg.arbocdi.builder.cfg.domain.BuilderEvent;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.interceptors.MessageHandlerInterceptor;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.Objects;

@Getter
@Slf4j
public abstract class CommandSagaBase {

    public static final String DEADLINE_NAME = "timeout";

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient EventGateway eventGateway;
    @Autowired
    private transient DeadlineManager deadlineManager;
    private String transactionId;
    private String deadlineId;

    public void send(BuilderEvent evt) throws Throwable {
        eventGateway.publish(evt);
    }
    public void send(BuilderCommand cmd) throws Throwable {
        sendCommand(cmd);
    }

    public void sendAndSwallow(BuilderCommand cmd) {
        try {
            sendCommand(cmd);
        } catch (Throwable e) {
            //swallow
            log.warn("sendAndSwallow",e);
        }
    }

    private void sendCommand(BuilderCommand command) throws Throwable {
        command.setTransactionId(transactionId);
        commandGateway.send(command).get();
    }


    @MessageHandlerInterceptor(messageType = EventMessage.class, payloadType = BuilderEvent.class)
    public void intercept(EventMessage<BuilderEvent> eventMessage, InterceptorChain interceptorChain) throws Exception {
        //on first message
        if (transactionId == null) {
            onFirstMessage(eventMessage.getPayload());
        }
        //if message belongs to saga
        if (Objects.equals(eventMessage.getPayload().getTransactionId(), transactionId)) {
            interceptorChain.proceed();
        }
    }

    private void onFirstMessage(BuilderEvent event) {
        if (deadlineId == null) deadlineId = deadlineManager.schedule(getTimeout(), "timeout", "Таймаут");
        transactionId = event.getTransactionId();
    }

    public void completeOk(Object result) {
        SagaLifecycle.end();
        if (deadlineId != null) deadlineManager.cancelSchedule("timeout", deadlineId);
    }

    private void completeException(String msg) {
        SagaLifecycle.end();
        log.warn("completeException message = {}",msg);
    }


    public Duration getTimeout() {
        return Duration.ofMinutes(1);
    }

    @DeadlineHandler(deadlineName = DEADLINE_NAME)
    public void onTimeout(String message) {
        onError();
        completeException(message);
    }

    protected abstract void onError();
}
