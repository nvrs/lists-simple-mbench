package nv.mbench.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;

/**
 * Created on 24/8/2016.
 */
@State(Scope.Thread)
public class CollectionBenchmark {

    private static final int INDICES_NO = 1_000_000;

    Index index;
    LinkedList<Integer> preallocatedLinkedList = new LinkedList<>();
    LinkedList<Integer> emptyLinkedList = new LinkedList<>();
    ArrayList<Integer> emptyArrayList = new ArrayList<>();
    ArrayList<Integer> preallocatedArrayList = new ArrayList<>();
    ArrayDeque<Integer> preallocatedDeque = new ArrayDeque<>();
    ArrayDeque<Integer> emtpyDeque = new ArrayDeque<>();

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

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = INDICES_NO)
    @Warmup(batchSize = INDICES_NO)
    public List<Integer> addToEmptyArrayList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        emptyArrayList.add(rIndex.ar[i]);

        return emptyArrayList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = INDICES_NO)
    @Warmup(batchSize = INDICES_NO)
    public Collection<Integer> addToEmptyDeque(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        emtpyDeque.addLast(rIndex.ar[i]);

        return emtpyDeque;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = INDICES_NO)
    @Warmup(batchSize = INDICES_NO)
    public List<Integer> addToEmptyLinkedList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        emptyLinkedList.add(rIndex.ar[i]);

        return emptyLinkedList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer randomIndexedGetFromPreallocatedArrayList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedArrayList.get(rIndex.ar[i]);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer randomIndexedGetFromPreallocatedLinkedList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedLinkedList.get(rIndex.ar[i]);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer setToPreallocatedArrayList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedArrayList.set(rIndex.ar[i], i);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer setToPreallocatedLinkedList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedLinkedList.set(rIndex.ar[i], i);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> addAtRandomIndexToPreallocatedArrayList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedArrayList.add(rIndex.ar[i], i);

        return preallocatedArrayList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> addAtRandomIndexToPreallocatedLinkedList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedLinkedList.add(rIndex.ar[i], i);

        return preallocatedLinkedList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> prependToPreallocatedArrayList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedArrayList.add(0, rIndex.ar[i]);

        return preallocatedArrayList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Collection<Integer> prependToPreallocatedDeque(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedDeque.addFirst(rIndex.ar[i]);

        return preallocatedDeque;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> prependToPreallocatedLinkedList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedLinkedList.add(0, rIndex.ar[i]);

        return preallocatedLinkedList;
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
        index = new Index();
    }

    @TearDown
    public void freePreallocatedLinkedList() {
        preallocatedLinkedList = null;
        preallocatedArrayList = null;
        preallocatedDeque = null;
    }

    @TearDown(Level.Iteration)
    public void freeEmLinkedList() {
        emptyLinkedList = null;
        emptyArrayList = null;
        emtpyDeque = null;

    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CollectionBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .shouldDoGC(true)
                .forks(0)
                .build();

        new Runner(opt).run();
    }
}
