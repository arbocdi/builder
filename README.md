### Builder
* Builder uses 4 projects:
  + Blueprints - stores blueprints for building ships.
  + Materials - manages materials in different stores.
  + Products - manages build outputs (products) inside ProductStores.
  + Builder - initializes blueprint and stores and performs building process itself.
  + Building process consists of 3 phases:
    + Find blueprint.
    + Take all input materials from MaterialStore.
    + Add all output materials to ProductStore.
* Builder uses Axon with Consul-based distributed command bus and Kafka as event distribution mechanism. 
* Build process implementing in 2 different ways: sending command and using axon sagas.


[docs](doc/readme.md)