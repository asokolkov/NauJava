#!/bin/bash

echo "Environment Variables:"
for var in $(env | sort); do
    echo "$var"
done
