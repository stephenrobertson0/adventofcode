package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _9 {

    private static class FileOrSpace {
        int index;
        int size;
        int id;

        public FileOrSpace(int index, int size, int id) {
            this.index = index;
            this.size = size;
            this.id = id;
        }

        public int getIndex() {
            return index;
        }

        public int getSize() {
            return size;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "FileOrSpace{" +
                    "index=" + index +
                    ", size=" + size +
                    ", id=" + id +
                    '}';
        }
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input9.txt"));

        final String line = fileReader.readLine();

        List<Integer> list = new ArrayList<>();

        boolean isFile = true;
        int id = 0;

        for (char c : line.toCharArray()) {

            int num = Integer.parseInt(""+c);

            if (isFile) {
                for (int j = 0; j < num; j++) {
                    list.add(id);
                }

                id ++;
            } else {
                for (int j = 0; j < num; j++) {
                    list.add(-1);
                }
            }

            isFile = !isFile;
        }

        int startIndex = 0;
        int endIndex = list.size() - 1;

        while (startIndex < endIndex) {

            if (list.get(startIndex) != -1) {
                startIndex++;
                continue;
            }

            int lastNum = list.remove(endIndex);
            endIndex--;
            list.set(startIndex, lastNum);
        }

        long total = 0;

        for (int j = 0; j < list.size(); j++) {
            if (list.get(j) != -1) {
                total += (long)j * list.get(j);
            }
        }

        System.out.println(total);

    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input9.txt"));

        final String line = fileReader.readLine();

        List<Integer> list = new ArrayList<>();
        List<FileOrSpace> gaps = new ArrayList<>();
        List<FileOrSpace> files = new ArrayList<>();

        boolean isFile = true;
        int id = 0;

        for (char c : line.toCharArray()) {

            int num = Integer.parseInt(""+c);

            if (isFile) {

                files.add(new FileOrSpace(list.size(), num, id));

                for (int j = 0; j < num; j++) {
                    list.add(id);
                }

                id ++;
            } else {

                gaps.add(new FileOrSpace(list.size(), num, -1));

                for (int j = 0; j < num; j++) {
                    list.add(-1);
                }
            }

            isFile = !isFile;
        }

        for (int j = 0; j < files.size(); j++) {

            FileOrSpace file = files.get(files.size()-j-1);

            for (int k = 0; k < gaps.size(); k++) {

                FileOrSpace gap = gaps.get(k);

                if (gap.getSize() >= file.getSize() && gap.index < file.getIndex()) {

                    for (int m = 0; m < file.getSize(); m++) {
                        list.set(gap.index + m, file.id);
                        list.set(file.index + m, -1);
                    }

                    gap.index = gap.index + file.size;
                    gap.size -= file.getSize();

                    break;

                }

            }

        }

        long total = 0;

        for (int j = 0; j < list.size(); j++) {
            if (list.get(j) != -1) {
                total += (long)j * list.get(j);
            }
        }

        System.out.println(total);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
