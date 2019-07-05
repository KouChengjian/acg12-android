package com.acg12.utils.download.task;

/**
 * 记录断点信息
 */
public class Ranges {
    public long start;
    public long end;

    public Ranges(long start, long end) {
        this.start = start;
        this.end = end;
    }
}
