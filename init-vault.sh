#!/bin/sh

echo "Waiting for Vault init..."

until vault status > /dev/null 2>&1; do
    vault status
    sleep 1
done

echo "Vault is ready. Starting to save properties..."

vault kv put secret/FinnHubConnectorApplication/local \
  finnhub.api-key=${FINNHUB_TOKEN}

echo "Done"