import time
import json
import random
from kafka import KafkaProducer

# bin/kafka-console-consumer.sh --topic sampletopic1 --from-beginning --bootstrap-server localhost:9092

kafka_bootstrap_server = 'localhost:9092'
# kafka_topic_name = 'sampletopic1'

# kafka_bootstrap_server = '192.168.0.131:9092'
kafka_topic_name = 'movietopic1'

producer = KafkaProducer(bootstrap_servers=kafka_bootstrap_server)

producer.send(kafka_topic_name, b'as22222dasd')

# producer = KafkaProducer(bootstrap_servers=kafka_bootstrap_server,
#                          # api_version=(0, 11, 5),
#                          # value_serializer=lambda v: json.dumps(v).encode('utf-8')
#                          )
#
# products = ["TV", "Laptop", "Oven", "Book", "PC", "Knife"]
# regions = ["DE", "BE", "NY", "GE"]
#
#
# def data_generator():
#     product = random.choice(products)
#     region = random.choice(regions)
#     price = random.randint(1, 20) * 10
#     amount = random.randint(1, 10)
#     total = amount * price
#     tax = price * amount * 0.15
#
#     return region, product, price, amount, total, tax
#
#
# def create_random_message_to_json():
#     (region, product, price, amount, total, tax) = data_generator()
#
#     return {
#         "Region": region,
#         "Product": product,
#         "Price": price,
#         "Amount": amount,
#         "Total": amount * price,
#         "Tax": tax
#     }
#
#
# def create_random_message_to_csv():
#     return ','.join(map(str, data_generator()))
#
#
# def data_producer():
#     message = create_random_message_to_csv()
#     producer.send(kafka_topic_name, bytes(message, encoding='utf-8'))
#     print('Published message 1: ' + message)


# while True:
#     data_producer()
#     print("wait 2 second ...")
#     time.sleep(2)


# data_producer()

# print( data_generator())

