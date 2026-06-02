import {useMemo, useState} from "react";
import {CartesianGrid, Label, Legend, Line, LineChart, Tooltip, XAxis, YAxis} from "recharts";

function ResultsChart({nationalResults, regionalResults}) {

    const metrics = [
        {value: "avgTotal", label: "Total"},
        {value: "avgSquat", label: "Squat"},
        {value: "avgBenchPress", label: "Bench Press"},
        {value: "avgDeadlift", label: "Deadlift"},
        {value: "avgIpfPoints", label: "IPF Points"}
    ];

    const [metric, setMetric] = useState("avgTotal");

    const chartData = useMemo(() => {
        const years = [...new Set([
            ...nationalResults.map(result => result.year),
            ...regionalResults.map(result => result.year)
        ])].sort();

        return years.map(year => ({
            year,
            national: nationalResults.find(r => r.year === year)?.[metric].toFixed(2) ?? 0,
            regional: regionalResults.find(r => r.year === year)?.[metric].toFixed(2) ?? 0
        }));
    }, [nationalResults, regionalResults, metric]);

    return (
        <>
            <fieldset>
                <legend>Metric</legend>
                {metrics.map(({value, label}) => (
                    <label key={value}>
                        <input type="radio" name="metric" value={value} checked={metric === value}
                               onChange={e => setMetric(e.target.value)}
                        />
                        {label}
                    </label>
                ))}
            </fieldset>

            <LineChart style={{width: '90%', aspectRatio: 1.618, margin: "auto"}} responsive data={chartData}>
                <CartesianGrid strokeDasharray="5 5"/>
                <XAxis dataKey="year">
                    <Label value="Year" dx={-30} dy={29}/>
                </XAxis>
                <YAxis domain={[min => min * 0.9, 'auto']}>
                    <Label value={"Average " + (metric === "avgIpfPoints" ? "IPF points" : "weight [kg]")}
                           angle={-90} dx={-28}
                    />
                </YAxis>
                <Tooltip formatter={(value) => `${value} ` + (metric === "avgIpfPoints" ? "pts" : "kg")}/>
                <Legend wrapperStyle={{paddingTop: "30px"}}/>
                <Line type="monotone" dataKey="national" name="National" strokeWidth={3} stroke="red"
                      dot={{r: 6, fill: "var(--bg)"}} activeDot={{stroke: 'var(--bg)'}}
                />
                <Line type="monotone" dataKey="regional" name="Regional" strokeWidth={3} stroke="green"
                      dot={{r: 6, fill: "var(--bg)"}} activeDot={{stroke: 'var(--bg)'}}
                />
            </LineChart>
        </>
    );
}

export default ResultsChart;
