import axios from "axios";
import {BASE_URL} from "./constants";

export const getCoinStat = async (symbol) => {
    return axios.get(`${BASE_URL}/coin/${symbol}`)
}