vegeta  -cpus 1 attack -rate 500 -duration=20s  -targets ./target.list | vegeta report -type=json | jq '.' > metrics.json
cat metrics.json
echo "::::METRICS="$(cat metrics.json)

