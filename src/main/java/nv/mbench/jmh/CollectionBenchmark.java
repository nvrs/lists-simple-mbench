package nv.mbench.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created on 24/8/2016.
 */
@SuppressWarnings("Duplicates")
@State(Scope.Thread)
public class CollectionBenchmark {

    private static final int OBJECTS_NO = 1_000_000;

    // state objects
    LinkedListSt preallocatedLinkedList;
    LinkedListSt emptyLinkedList;
    ArrayListSt emptyArrayList;
    ArrayListSt preallocatedArrayList;

    @State(Scope.Thread)
    public static class Index {
        final int[] ar = new int[OBJECTS_NO];

        public Index() {
            Random r = new Random();

            for (int i = 0; i < OBJECTS_NO; i++) {
                ar[i] = r.nextInt(OBJECTS_NO - 1);
            }
        }
    }

    @State(Scope.Thread)
    public static class LinkedListSt extends ListWrapper<Integer> {

        public LinkedListSt() {
            super(new LinkedList<>());
        }
    }

    @State(Scope.Thread)
    public static class ArrayListSt extends ListWrapper<Integer> {

        public ArrayListSt() {
            super(new ArrayList<>());
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = OBJECTS_NO)
    @Warmup(batchSize = OBJECTS_NO)
    public ListWrapper<Integer> addToEmptyArrayList(Index index) {
        int i = emptyArrayList.incrementIndex() % OBJECTS_NO;
        emptyArrayList.add(index.ar[i]);

        return emptyArrayList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = OBJECTS_NO)
    @Warmup(batchSize = OBJECTS_NO)
    public List<Integer> addToEmptyLinkedList(Index index) {
        int i = emptyLinkedList.incrementIndex() % OBJECTS_NO;
        emptyLinkedList.add(index.ar[i]);

        return emptyLinkedList.underlying();
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 100)
    public Integer randomGetFromPreallocatedArrayList(Index index) {
        int i = preallocatedArrayList.incrementIndex() % OBJECTS_NO;
        return preallocatedArrayList.get(index.ar[i]);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 100)
    public Integer randomGetFromPreallocatedLinkedList(Index index) {
        int i = preallocatedLinkedList.incrementIndex() % OBJECTS_NO;
        return preallocatedLinkedList.get(index.ar[i]);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 100)
    public Integer setToPreallocatedArrayList(Index index) {
        int i = preallocatedArrayList.incrementIndex() % OBJECTS_NO;
        return preallocatedArrayList.set(index.ar[i], i);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 100)
    public Integer setToPreallocatedLinkedList(Index index) {
        int i = preallocatedLinkedList.incrementIndex() % OBJECTS_NO;
        return preallocatedLinkedList.set(index.ar[i], i);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 100)
    public List<Integer> addAtRandomToPreallocatedArrayList(Index index) {
        int i = preallocatedArrayList.incrementIndex() % OBJECTS_NO;
        preallocatedArrayList.add(index.ar[i], i);

        return preallocatedArrayList.underlying();
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 100)
    public List<Integer> addAtRandomToPreallocatedLinkedList(Index index) {
        int i = preallocatedLinkedList.incrementIndex() % OBJECTS_NO;
        preallocatedLinkedList.add(index.ar[i], i);

        return preallocatedLinkedList.underlying();
    }

    @Setup
    public void setupPreallocatedLinkedList(Index index) {
        preallocatedLinkedList = new LinkedListSt();

        for (int i = 0; i < index.ar.length; i++) {
            preallocatedLinkedList.add(0);
        }

        preallocatedLinkedList.resetIndex();
    }

    @Setup
    public void setupPreallocatedArrayList(Index index) {
        preallocatedArrayList = new ArrayListSt();

        for (int i = 0; i < index.ar.length; i++) {
            preallocatedArrayList.add(0);
        }

        preallocatedArrayList.resetIndex();
    }

    @Setup(Level.Iteration)
    public void setupEmptyLinkedList() {
        emptyLinkedList = new LinkedListSt();
        emptyArrayList = new ArrayListSt();
    }

    @TearDown
    public void freePreallocatedLinkedList() {
        preallocatedLinkedList = null;
        preallocatedArrayList = null;
    }

    @TearDown(Level.Iteration)
    public void freeEmLinkedList() {
        emptyLinkedList = null;
        emptyArrayList = null;
        preallocatedArrayList.resetIndex();
        preallocatedLinkedList.resetIndex();
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
