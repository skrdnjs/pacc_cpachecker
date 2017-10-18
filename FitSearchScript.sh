#!/bin/bash

TARGET_FILE="../sv-benchmarks/c/ntdrivers/floppy_true-unreach-call_true-valid-memsafety.i.cil.c"

for i in {1..30}
do
	sudo python3 ./scripts/RanTSExecutor.py --cores 0 --memlimit 8000000000 --no-container -- scripts/cpa.sh -heap 7000M -timelimit 30s -noout -Dy-MySearchStrategy-PredAbs-ABElf -preprocess -stats -setprop cpa.predicate.memoryAllocationsAlwaysSucceed=true -spec ../sv-benchmarks/c/ReachSafety.prp $TARGET_FILE

	sudo python3 ./scripts/TSExecutorFull.py --cores 0 --memlimit 8000000000 --no-container -- scripts/cpa.sh -heap 7000M -timelimit 900s -noout -Dy-MySearchStrategy-PredAbs-ABElf -preprocess -stats -setprop cpa.predicate.memoryAllocationsAlwaysSucceed=true -spec ../sv-benchmarks/c/ReachSafety.prp $TARGET_FILE
done
