package com.project.hanfu.util;

public class SnowFlake {
    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;  // 序列号初始为0
    private long lastTimestamp = -1L;  // 上次生成ID的时间戳

    private final long WORKER_ID_BITS = 5L; // 5位工作机器的ID
    private final long DATACENTER_ID_BITS = 5L; // 5位数据中心的ID
    private final long SEQUENCE_BITS = 12L; // 12位序列号
    private final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS); // 最大工作机器ID
    private final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS); // 最大数据中心ID
    private final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS); // 最大序列号

    // 时间戳的起始时间
    private final long EPOCH = 1612137600000L; // 2021-02-01 00:00:00

    public SnowFlake(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("Worker ID can't be greater than "
                    + MAX_WORKER_ID + " or less than 0");
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID can't be greater than "
                    + MAX_DATACENTER_ID + " or less than 0");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (currentTimestamp == lastTimestamp) {
            // 同一毫秒内，序列号递增
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 序列号用尽，等待下一毫秒
                currentTimestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - EPOCH) << (WORKER_ID_BITS + DATACENTER_ID_BITS + SEQUENCE_BITS))
                | (datacenterId << (WORKER_ID_BITS + SEQUENCE_BITS))
                | (workerId << SEQUENCE_BITS)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}