//package org.example.websocket.configuration;
//
//import org.apache.kafka.clients.producer.Partitioner;
//import org.apache.kafka.common.Cluster;
//import org.example.inventory_write_service.dto.Event;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Map;
//
//
//@Configuration
//public class CustomPartitioner implements Partitioner {
//    @Override
//    public int partition(String s, Object o, byte[] bytes, Object value, byte[] bytes1, Cluster cluster) {
//
//
//        if(value instanceof Event event){
//
//            switch (event.getEntity()){
//
//                case "category":
//                case "sub-category":
//                case "sub-product-category":
//                case "manufacturer":
//                    return 0;
//
//                case "product":
//                case "store-product":
//                case "dark-store":
//                    return 1;
//
//
//            }
//
//        }
//
//        return (bytes == null) ? 0 : Math.abs(o.hashCode()) % cluster.partitionCountForTopic(s);
//    }
//
//    @Override
//    public void close() {
//
//    }
//
//    @Override
//    public void configure(Map<String, ?> map) {
//
//    }
//}
