package base;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Your task in this lab is to implement a parallel string merger, which provides a method to merge strings in parallel (with threads).<p>
 * <p>An array of string segments will be given as input, e.g., `["hlo", "el!]`, the `merge` method should create a set of threads whose size is the same as the number of segments.
 * In this example, you should create `thread1` for `"hlo"` and `thread2` for `"el!"`.
 * Then, all threads take turns to merge the segments character by character.</p>
 * <p>A writer is provided for threads to write the character. <p>
 * <p>The expected behavior for the example is:<p>
 * <ul>
 * <li>`thread1` writes `h`</li>
 * <li>`thread2` writes `e`</li>
 * <li>`thread1` writes `l`</li>
 * <li>`thread2` writes `l`</li>
 * <li>`thread1` writes `o`</li>
 * <li>`thread2` writes `!`</li>
 * </ul>
 * <p>Eventually, the writer will get `h`, `e`, `l`, `l`, `o`, `!`, written by the two threads.</p>
 * <p>The last thing you need to make sure is that all threads should exit before `merge` method returns.</p>
 * */
public class ParallelMerger {
    @FunctionalInterface
    public interface ThreadSafeCharacterWriter {
        void write(char ch); //write the character
    }

    /**
     * You don't need to implement this interface by yourself.
     * You can treat it as a list of char which should stores the string merge results of all threads after all threads exit.
     * The content of this list will be cleared before the thread is started.
     * So in your thread, what you need to do is to call {@link ThreadSafeCharacterWriter#write(char)} to write the character to the list.
     */
    private static ThreadSafeCharacterWriter resultWriter;


    /**
     * TODO: Add suitable attributes to this class to help your implementation.
     * You can add any attributes you want to this class.
     * You can refer to the tips in the lab slides.
     */


    /**
     * The worker class which is {@link Runnable}.
     * All worker objects will work together to merge their own char array and put results in {@link ParallelMerger#resultWriter}.
     * <p>
     * The merge rule is at follows:
     * <p>
     * 1. each worker thread take turns to write one char into {@link ParallelMerger#resultWriter}
     * <p>
     * 2. For example if we have two workers, worker0, worker1, with char array "hlo" and "el!" respectively.
     * Then after the merge, {@link ParallelMerger#resultWriter} should contain: "hello!".
     * 'h' should be written by the first worker, 'e' should be written by the second worker and 'l' should be written by the first worker, so on and so forth.
     * <p>
     * TODO: complete this class to implement the above functionality.
     */
    private static class Worker implements Runnable {
        char[] segment; //Note that segment = {'h','l','o'}
        int Num_Threads;
        int Thread_ID;
        static AtomicInteger shared_index = new AtomicInteger(0); //Index Share by All Thread

        Worker(char[] segment, int Num_Threads, int Thread_ID){
            this.segment = segment;
            this.Num_Threads = Num_Threads;
            this.Thread_ID = Thread_ID;
        }

        @Override
        public void run() {
            for(int i = 0; i < segment.length;){ //Semi-infinite loop -> keep running
                if(shared_index.get() % Num_Threads == Thread_ID ){ //ABABABABAB pattern
                    resultWriter.write(segment[i]); //Write char to the buffer

                    shared_index.incrementAndGet(); //shared index++ with synchronization
                    i++; //local i++
                }

            }
        }
    }



    /**
     * This method does the merge in an array of string segments. Here is the desired procedure:
     * <ol>
     *     <li>Create workers and their threads, the number of threads should be the same as the length of array {@code segments}.</li>
     *     <li>Start all threads to merge the {@code segments} in parallel.</li>
     *     <li>Wait all threads to finish process and exit.</li>
     * </ol>
     *
     * @param segments     An array of strings that are to be merged. Each segment should be corresponding to one thread.
     * @param resultWriter A {@link ThreadSafeCharacterWriter} instance that the workers should write the results to.
     *                     The {@code resultWriter} should be written with the characters of merged string.
     *                     For example, suppose the merge result should be "hello", then the {@code resultWriter}
     *                     should be called 5 times with each character in the order.
     *
     * TODO: complete this method to implement the above functionality.
     */
    public static void merge(String[] segments, ThreadSafeCharacterWriter resultWriter) throws InterruptedException {
        ParallelMerger.resultWriter = resultWriter;
        // Start from here
        int Num_Threads = segments.length;
        Thread[] threads = new Thread[Num_Threads]; //Create corresponding thread

        for (int i = 0; i < Num_Threads; i++) {
            Worker worker = new Worker(segments[i].toCharArray(),Num_Threads,i); //Pass all the parameter to the thread

            threads[i] = new Thread(worker); //instantiate the thread
            threads[i].start(); //start the thread
        }

        for(int i  = 0; i < Num_Threads; i++){
            threads[i].join(); //Ensure all the threads has terminated before exit the function
        }
    }
        // ...
}