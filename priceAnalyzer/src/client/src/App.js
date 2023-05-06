import './App.css';
import CoinStat from "./components/coinStat/CoinStat";
import IndicatorStatContainer from "./containers/IndicatorStatContainer";
import Header from "./components/Header";
import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
} from "react-router-dom";
import CoinStatContainer from "./containers/CoinStatContainer";

function App() {
    return (
        <Router>
            <Header/>
                <Routes>
                    <Route exact path="/" element={<IndicatorStatContainer/>}/>
                    <Route exact path="/coin/:id" element={<CoinStatContainer/>}/>
                </Routes>
        </Router>
    );
}

export default App;
