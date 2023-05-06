import CoinStat from "../components/coinStat/CoinStat";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {getIndicatorStat} from "../api/IndicatorStatService";
import {getCoinStat} from "../api/coinStatService";

// const periods = 365;
const periods = 200;

const CoinStatContainer = () => {
    const [coinStats, setCoinStats] = useState({});
    const [indicatorStats, setIndicatorStats] = useState({});
    const [chartData, setChartData] = useState([{data:[{d:0, v:0}]}]);
    const [macdData, setMacdData] = useState([{data:[{d:0, v:0}]}]);
    let { id } = useParams();

    useEffect(() => {
        fetchData();
    },[id]);

    const fetchData = async () => {
        let ind = await getIndicatorStat(id);
        let coin = await getCoinStat(id);

        setCoinStats(coin.data);
        setIndicatorStats(ind.data);

        convertToChartData(coin.data)
    }

    const convertToChartData = (data) => {
        let tmp = [];
        let macdTmp = [];

        let date = data.date?.slice(data.date.length - periods)
        addToChartData("ema10", data.ema10,date, tmp);
        addToChartData("ema40", data.ema40,date, tmp);
        addToChartData("macd", data.macd,date, macdTmp);
        addToChartData("macdSignal", data.macdSignal,date, macdTmp);
        addToChartData("price", data.price,date, tmp);
        // addToChartData("ema", ,date, chardData);


        console.log(tmp)

        setChartData(tmp)
        setMacdData(macdTmp)
    }


    const addToChartData = (label, list, date, chartData) => {
        let slicedList = list.slice(list.length - periods)
        let combinedData = slicedList.map((e, i) => ({d:date[i], v:e}))

        chartData.push({
            label: label,
            data: combinedData
        })
    }

    return <CoinStat chartData={chartData} macdData={macdData} coinStats={coinStats} indicatorStats={indicatorStats}/>
}

export default CoinStatContainer;