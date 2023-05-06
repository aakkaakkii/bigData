package com.example.priceanalyzer.services;

import com.example.priceanalyzer.dto.CoinStat;
import com.example.priceanalyzer.dto.IndicatorStat;
import com.example.priceanalyzer.entitys.CoinStatEntity;
import com.example.priceanalyzer.entitys.IndicatorStatEntity;
import com.example.priceanalyzer.repo.CoinStatRepository;
import com.example.priceanalyzer.repo.IndicatorStatRepository;
import com.google.gson.Gson;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SyncService {
    public static final String HDFS_ROOT_URL="hdfs://localhost:9000";
    private final IndicatorStatRepository indicatorStatRepository;
    private final CoinStatRepository coinStatRepository;

    public SyncService(IndicatorStatRepository indicatorStatRepository, CoinStatRepository coinStatRepository) {
        this.indicatorStatRepository = indicatorStatRepository;
        this.coinStatRepository = coinStatRepository;
    }

    public void sync() throws Exception{
        clear();
        Configuration conf = new Configuration();
        String coin = getFileContent("/price/priceStatRes/part-00000", conf);
        String indicator = getFileContent("/price/indicatorStatRes/part-00000", conf);

        CoinStat[] coinStats = new Gson()
                .fromJson( coin, CoinStat[].class);
        IndicatorStat[] indicatorStats = new Gson()
                .fromJson( indicator, IndicatorStat[].class);

        coinStatRepository
                .saveAll( Arrays
                                .stream( coinStats)
                                .map( CoinStat::toEntity)
                                .collect( Collectors.toList()));
        indicatorStatRepository
                .saveAll( Arrays
                        .stream( indicatorStats)
                        .map( IndicatorStat::toEntity)
                        .collect( Collectors.toList()));
    }

    public String getFileContent(String path, Configuration conf) throws Exception {
        String uri = HDFS_ROOT_URL + path;
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        InputStream in = null;

        try {
            in = fs.open(new Path(uri));
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } finally {
            IOUtils.closeStream(in);
        }
    }

    public void clear() {
        coinStatRepository.deleteAll();
        indicatorStatRepository.deleteAll();
    }
}
