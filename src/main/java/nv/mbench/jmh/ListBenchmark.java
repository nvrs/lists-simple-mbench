package nv.mbench.jmh;

import org.eclipse.collections.impl.list.mutable.FastList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created on 24/8/2016.
 */
@State(Scope.Thread)
public abstract class ListBenchmark {

    public static final int INDICES_NO = 1_000_000;

    Index index;
    LinkedList<Integer> preallocatedLinkedList = new LinkedList<>();
    LinkedList<Integer> emptyLinkedList = new LinkedList<>();
    ArrayList<Integer> emptyArrayList = new ArrayList<>();
    ArrayList<Integer> preallocatedArrayList = new ArrayList<>();
    ArrayDeque<Integer> preallocatedDeque = new ArrayDeque<>();
    ArrayDeque<Integer> emtpyDeque = new ArrayDeque<>();
    FastList<Integer> emptyFastList = new FastList<>();
    FastList<Integer> preallocatedFastList = new FastList<>();

    @State(Scope.Thread)
    public static class Index {
        private int index;

        public int incrementAndGet() {
            return ++index;
        }

        public int getAndIncrement() {
            return index++;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    @State(Scope.Thread)
    public static class RandomIndexArray {
        final int[] ar = new int[INDICES_NO];

        public RandomIndexArray() {
            Random r = new Random();

            for (int i = 0; i < INDICES_NO; i++) {
                ar[i] = r.nextInt(INDICES_NO - 1);
            }
        }
    }

    @Setup
    public void setupPreallocatedLinkedList(RandomIndexArray rIndex) {
        preallocatedLinkedList = new LinkedList<>();

        for (int i = 0; i < rIndex.ar.length; i++) {
            preallocatedLinkedList.add(0);
        }
    }

    @Setup
    public void setupPreallocatedArrayList(RandomIndexArray rIndex) {
        preallocatedArrayList = new ArrayList<>();

        for (int i = 0; i < rIndex.ar.length; i++) {
            preallocatedArrayList.add(0);
        }
    }

    @Setup
    public void setupPreallocatedFastList(RandomIndexArray rIndex) {
        preallocatedFastList = new FastList<>();

        for (int i = 0; i < rIndex.ar.length; i++) {
            preallocatedFastList.add(0);
        }
    }

    @Setup
    public void setupPreallocatedDeque(RandomIndexArray rIndex) {
        preallocatedDeque = new ArrayDeque<>();

        for (int i = 0; i < rIndex.ar.length; i++) {
            preallocatedDeque.addLast(0);
        }
    }

    @Setup(Level.Iteration)
    public void setupEmptyLinkedList() {
        emptyLinkedList = new LinkedList<>();
        emptyArrayList = new ArrayList<>();
        emtpyDeque = new ArrayDeque<>();
        emptyFastList = new FastList<>();
        index = new Index();
    }

    @TearDown
    public void freePreallocatedLinkedList() {
        preallocatedLinkedList = null;
        preallocatedArrayList = null;
        preallocatedDeque = null;
        preallocatedFastList = null;
    }

    @TearDown(Level.Iteration)
    public void freeEmLinkedList() {
        emptyLinkedList = null;
        emptyArrayList = null;
        emtpyDeque = null;
        emptyFastList = null;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include("AddFirst|AddLast|AddRandomIndex|GetRandomIndex|SetRandomIndex")
                .warmupIterations(5)
                .measurementIterations(5)
                .shouldDoGC(true)
                .forks(0)
                .build();

        new Runner(opt).run();
    }

    public static void start(String regex) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(regex)
                .warmupIterations(5)
                .measurementIterations(5)
                .shouldDoGC(true)
                .forks(0)
                .build();

        new Runner(opt).run();
    }
}
