import {useEffect, useState} from "react";
import IndicatorStat from "../components/indicatorStat/IndicatorStat";
import {getAll} from "../api/IndicatorStatService";
import {useParams} from "react-router-dom";

const IndicatorStatContainer = () => {
    const [indicatorStat, setIndicatorStat] = useState([]);

    useEffect(() => {
        fetchData();
    },[]);

    const fetchData = async () => {
        const data = await getAll();
        setIndicatorStat(data.data);
    }

    return <IndicatorStat indicatorStat={indicatorStat}/>
}

export default IndicatorStatContainer;