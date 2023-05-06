import {Card, CardContent, Grid, Typography} from "@mui/material";
import Chart from "./StatChart";

const CoinStat = ({chartData, macdData, coinStats, indicatorStats}) => {

    return <Grid container spacing={2} style={{margin: 10}}>
        <Grid item xs={9} >
            <Grid container>
                <Grid item xs={12} style={{height:500}}>
                    <Chart chartData={chartData}/>

                </Grid>
                <Grid item xs={12} style={{height:200}}>
                    <Chart chartData={macdData}/>
                </Grid>
            </Grid>

            {/*<Chart chartData={chartData}/>*/}
        </Grid>
        {/*<Grid item xs={4} >*/}
        {/*    <Chart chartData={macdData}/>*/}
        {/*</Grid>*/}
        <Grid item xs={3}>
            <Card >
                <CardContent style={{height:500}}>
                    <Typography sx={{ fontSize: 18 }} gutterBottom>
                        {indicatorStats.symbol}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        EMA - {indicatorStats.ema}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        MACD - {indicatorStats.macd}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        RSI - {indicatorStats.rsi}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        NN - {indicatorStats.nnSignal}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        Last Price - {indicatorStats.lastPrice}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        Predicted Price - {indicatorStats.predictedPrice}
                    </Typography>
                    <Typography variant="body2" color={indicatorStats.finalSignal > 0.5 ? "green" : "red"}>
                        Final Score - {Math.round(indicatorStats.finalSignal * 10)/ 10}
                    </Typography>
                </CardContent>
            </Card>
        </Grid>
    </Grid>
}

export default CoinStat;