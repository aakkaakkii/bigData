import time
import json
import random
from kafka import KafkaProducer

kafka_bootstrap_server = '192.168.0.131:9092'
kafka_topic_name = 'movietopic1'


movies = ["test", "test2"]


producer = KafkaProducer(bootstrap_servers=kafka_bootstrap_server,
                         # api_version=(0, 11, 5),
                         value_serializer=lambda v: json.dumps(v).encode('utf-8')
                         )


def data_generator():
    movie_id = random.randint(0, len(movies) - 1)
    movie_title = movies[movie_id]
    movie_rating = random.randint(0, 10)
    user_id = random.randint(0, 100)

    return movie_id, movie_title, movie_rating, user_id


def create_random_message_to_json():
    (movie_id, movie_title, movie_rating, user_id) = data_generator()

    return {
        "movieId": movie_id,
        "movieTitle": movie_title,
        "movieRating": movie_rating,
        "userId": user_id
    }


def data_producer():
    message = create_random_message_to_json()
    producer.send(kafka_topic_name, message)
    print('Published message 1: ' + json.dumps(message))

data_producer()
data_producer()

# while True:
#     data_producer()
#     print("wait 2 second ...")
#     time.sleep(2)
