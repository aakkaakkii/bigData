import axios from "axios";
import {BASE_URL} from "./constants";

export const getAll = async () => {
    return axios.get(`${BASE_URL}/indicator`)
}

export const getIndicatorStat = async (symbol) => {
    return axios.get(`${BASE_URL}/indicator/${symbol}`)
}