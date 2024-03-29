import http from 'k6/http';
import { randomString } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
    vus: 50,
    duration: '30s',
};
export default function () {
    const url = 'http://localhost:8080/fishes';
    const payload = JSON.stringify({
        name: randomString(200),
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    http.post(url, payload, params);
}