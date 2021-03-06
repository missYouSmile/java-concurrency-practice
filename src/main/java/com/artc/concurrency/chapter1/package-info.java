package com.artc.concurrency.chapter1;

/**
 * A. 性能
 * 1. CPU(单核)执行方式: 为每个线程分配时间片来执行线程, 因为时间片非常短, CPU 快速的切换上下文 实现多线程
 *      ** 所以多线程是有系统级的开销 => 多线程不一定比单线程快
 */