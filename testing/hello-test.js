import http from 'k6/http';
import { check } from 'k6';
import { Counter } from 'k6/metrics';

export const options = {
    vus: 2,
    iterations: 20,
};

// Global counters (k6 metrics)
const counter_A = new Counter('counter_A');
const counter_B = new Counter('counter_B');

export default function () {
    const res = http.get('http://localhost:8000/welcome/hello');

    // Parse response body
    let instance = 'Unknown';
    try {
        const body = JSON.parse(res.body);
        if (body.message.includes('A')) {
            instance = 'A';
            counter_A.add(1);
        } else if (body.message.includes('B')) {
            instance = 'B';
            counter_B.add(1);
        }
    } catch (e) {
        console.log('Failed to parse response:', res.body);
    }

    console.log(`Status: ${res.status} | Body: ${res.body} | Instance: ${instance}`);

    check(res, {
        'status is 200': (r) => r.status === 200,
    });
}

// Optional: final summary
export function handleSummary(data) {
    // Helper: try common places where k6 may store aggregated counter totals
    function getMetricSum(metrics, name) {
        if (!metrics || !metrics[name]) return 0;
        const m = metrics[name];
        // Direct sum property
        if (typeof m.sum === 'number') return m.sum;
        // Common shape: { values: { sum: X } } or { values: { count: X } }
        if (m.values) {
            if (typeof m.values.sum === 'number') return m.values.sum;
            if (typeof m.values.count === 'number') return m.values.count;
        }
        // Sometimes the metric shape differs between k6 versions; search recursively for the first numeric value
        function findNumber(obj, visited = new Set()) {
            if (!obj || typeof obj !== 'object') return null;
            if (visited.has(obj)) return null;
            visited.add(obj);
            for (const k of Object.keys(obj)) {
                const v = obj[k];
                if (typeof v === 'number') return v;
                if (typeof v === 'object') {
                    const found = findNumber(v, visited);
                    if (found !== null) return found;
                }
            }
            return null;
        }
        const fallback = findNumber(m);
        return fallback !== null ? fallback : 0;
    }

    const aCount = getMetricSum(data.metrics, 'counter_A');
    const bCount = getMetricSum(data.metrics, 'counter_B');

    console.log(`\n=== Final Count ===\nInstance A: ${aCount}\nInstance B: ${bCount}\n`);
    return {};
}
