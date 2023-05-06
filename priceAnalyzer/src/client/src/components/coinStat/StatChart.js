
import React, {useMemo} from 'react';

import { AxisOptions, Chart } from "react-charts";





const StatChart = ({chartData}) => {
    const data = [
        {
            label: 'React Charts',
            data: [
                {
                    d: 1,
                    v: 12,
                },
                {
                    d: 2,
                    v: 44,
                },
            ],
        },
    ]

    const data2 =[
        {
            "label": "ema10",
            "date": [
                {
                    "d": 0,
                    "v": 43125.21400767866
                },
                {
                    "d": 1,
                    "v": 42992.53124111407
                }
            ]
        },

    ]

    const primaryAxis = useMemo(
        () => ({
            getValue: datum => datum.d,
        }),
        []
    )

    const secondaryAxes = useMemo(
        ()=> [
            {
                getValue: datum => datum.v,
            },
        ],
        []
    )

    return <Chart
        options={{
            data:chartData,
            primaryAxis,
            secondaryAxes,
        }}
    />
}

export default StatChart;