import {Button, Card, CardActions, CardContent, Grid, Typography} from "@mui/material";
import {Link} from "react-router-dom";


const IndicatorStat = ({indicatorStat}) => {
    return <Grid container spacing={2}>
        {
            indicatorStat.map((e, i) => <Grid item xs={3} key={i}>
                <Card style={{margin: 10}}>
                    <CardContent>
                        <Typography sx={{ fontSize: 18 }} gutterBottom>
                            {e.symbol}
                        </Typography>


                        <Typography variant="body2" color="text.secondary">
                            EMA - {e.ema}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            MACD - {e.macd}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            RSI - 0
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            NN - {e.nnSignal}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Anomaly - {e.nnSignal}
                        </Typography>
                        <Typography variant="body2" color={e.finalSignal > 0.5 ? "green" : "red"}>
                            Final Score - {Math.round(e.finalSignal * 10)/ 10}
                        </Typography>

                    </CardContent>
                    <CardActions>
                        <Button size="small"  component={Link} to={`/coin/${e.symbol}`}>Learn More</Button>
                    </CardActions>
                </Card>
            </Grid>)
        }
    </Grid>
}

export default IndicatorStat;