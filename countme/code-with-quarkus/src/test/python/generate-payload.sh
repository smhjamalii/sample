set -eu
SEED=$(echo $((1 + RANDOM % 10)))
echo -n $SEED > payload.txt
set +e
