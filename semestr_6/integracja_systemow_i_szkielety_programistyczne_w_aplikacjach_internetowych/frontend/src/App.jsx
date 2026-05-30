import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import ResultsCreatePage from "./pages/ResultsCreatePage.jsx";
import ResultsPage from "./pages/ResultsPage.jsx";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/results" element={<ResultsPage/>}/>
                <Route path="/results/create" element={<ResultsCreatePage/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default App
