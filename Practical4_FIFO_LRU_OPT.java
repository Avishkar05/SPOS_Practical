import java.util.*;

public class FIFO_LRU_OPT {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of frames
        System.out.print("Enter number of frames: ");
        int framesCount = scanner.nextInt();

        // Input page reference string
        System.out.print("Enter number of pages in reference string: ");
        int n = scanner.nextInt();
        int[] pages = new int[n];
        System.out.println("Enter the page reference string:");
        for (int i = 0; i < n; i++) {
            pages[i] = scanner.nextInt();
        }

        // Run all algorithms
        System.out.println("\n--- FIFO Page Replacement ---");
        fifo(pages, framesCount);

        System.out.println("\n--- LRU Page Replacement ---");
        lru(pages, framesCount);

        System.out.println("\n--- OPTIMAL Page Replacement ---");
        optimal(pages, framesCount);
        
        scanner.close();
    }

    // FIFO Algorithm
    public static void fifo(int[] pages, int frameCount) {
        Set<Integer> frames = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!frames.contains(page)) {
                if (frames.size() == frameCount) {
                    int removed = queue.poll();
                    frames.remove(removed);
                }
                frames.add(page);
                queue.add(page);
                pageFaults++;
            }
        }

        System.out.println("Total Page Faults (FIFO): " + pageFaults);
    }

    // LRU Algorithm
    public static void lru(int[] pages, int frameCount) {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (frames.contains(page)) {
                frames.remove((Integer) page);
            } else {
                if (frames.size() == frameCount) {
                    frames.remove(0);
                    pageFaults++;
                } else {
                    pageFaults++;
                }
            }
            frames.add(page);
        }

        System.out.println("Total Page Faults (LRU): " + pageFaults);
    }

    // OPTIMAL Algorithm
    public static void optimal(int[] pages, int frameCount) {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];

            if (!frames.contains(page)) {
                if (frames.size() < frameCount) {
                    frames.add(page);
                } else {
                    int indexToReplace = findOptimal(frames, pages, i + 1);
                    frames.set(indexToReplace, page);
                }
                pageFaults++;
            }
        }

        System.out.println("Total Page Faults (OPT): " + pageFaults);
    }

    // Helper for OPTIMAL algorithm
    public static int findOptimal(List<Integer> frames, int[] pages, int startIndex) {
        int farthest = startIndex;
        int indexToReplace = -1;

        for (int i = 0; i < frames.size(); i++) {
            int page = frames.get(i);
            boolean found = false;
            for (int j = startIndex; j < pages.length; j++) {
                if (page == pages[j]) {
                    if (j > farthest) {
                        farthest = j;
                        indexToReplace = i;
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                return i; // Never used again
            }
        }

        return (indexToReplace == -1) ? 0 : indexToReplace;
    }
}
