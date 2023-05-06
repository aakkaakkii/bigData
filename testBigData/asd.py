dag = DAG(
    dag_id='my_crypto_dag',
    schedule_interval='@once',
    start_date=datetime(2022, 2, 9),
    tags=['crypto'],
)
# schedule_interval = '*/10 * * * * *',


clear_priceStat = BashOperator(
    task_id='clear_priceStat',
    bash_command='hadoop fs -rm "/price/priceStat"',
    dag=dag,
)

clear_indicatorStat = BashOperator(
    task_id='clear_indicatorStat',
    bash_command='hadoop fs -rm "/price/indicatorStat"',
    dag=dag,
)

clear_nn = BashOperator(
    task_id='clear_nn',
    bash_command='hadoop fs -rm "/price/NN"',
    dag=dag,
)

clear_cluster = BashOperator(
    task_id='clear_cluster',
    bash_command='hadoop fs -rm "/price/cluster"',
    dag=dag,
)

clear_priceStatRes = BashOperator(
    task_id='clear_priceStatRes',
    bash_command='hadoop fs -rm "/price/priceStatRes"',
    dag=dag,
)

clear_indicatorStatRes = BashOperator(
    task_id='clear_indicatorStatRes',
    bash_command='hadoop fs -rm "/price/indicatorStatRes"',
    dag=dag,
)



run_file_combiner_task= BashOperator(
    task_id='run_file_combiner_task',
    bash_command='  ~/programming/bigData/spark/bin/spark-submit \
  --class FileCombiner \
  --master   spark://kaki:7077  \
  /home/aka/programming/java/criptoPrice/target/scala-2.12/criptoprice_2.12-0.1.jar \
  1000',
    dag=dag,
)


run_indicator_calculation_task= BashOperator(
    task_id='run_indicator_calculation_task',
    bash_command='  ~/programming/bigData/spark/bin/spark-submit \
  --class PriceAnalyzer \
  --master   spark://kaki:7077  \
  /home/aka/programming/java/criptoPrice/target/scala-2.12/criptoprice_2.12-0.1.jar \
  1000',
    dag=dag,
)


run_anomaly_detection_task= BashOperator(
    task_id='run_anomaly_detection_task',
    bash_command='~/programming/bigData/spark/bin/spark-submit \
  --master   spark://kaki:7077  \
  /home/aka/programming/python/testBigData/anomaly_detection_spark.py \
  1000',
    dag=dag,
)

run_NN_task = BashOperator(
    task_id='run_NN_task',
    bash_command='~/programming/bigData/spark/bin/spark-submit \
  --master   spark://kaki:7077  \
  /home/aka/programming/python/testBigData/nn.py \
  1000',
    dag=dag,
)

run_sync_task = BashOperator(
    task_id='run_sync_task',
    bash_command='curl http://localhost:8080/api/sync',
    dag=dag,
)



clear_priceStatRes >> clear_indicatorStatRes >> run_NN_task >> run_anomaly_detection_task >> run_indicator_calculation_task >> run_file_combiner_task >> clear_priceStat >> clear_indicatorStat >> clear_nn >> clear_cluster