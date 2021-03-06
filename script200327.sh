#!/bin/bash

python3 Makekresultup.py 0 $1

#main analysis

sudo swapoff -a
python3 WhatMachine.py doc/examples/benchmark-LPAABE-BATS.xml $1
sudo benchexec --no-container doc/examples/benchmark-LPAABE-BATS.xml
sudo sftp -b sftpbatches/kresultup spiralftp@spiral.kaist.ac.kr
sudo cp results/benchmark* resultsbkup/
sudo rm results/benchmark*

sudo swapoff -a
python3 WhatMachine.py doc/examples/benchmark-LPAABE-Ex.xml $1
sudo benchexec --no-container doc/examples/benchmark-LPAABE-Ex.xml
sudo sftp -b sftpbatches/kresultup spiralftp@spiral.kaist.ac.kr
sudo cp results/benchmark* resultsbkup/
sudo rm results/benchmark*
