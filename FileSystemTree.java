package application;

import java.io.*;
import java.util.*;

public class FileSystemTree {

    static class Folder {
        private String name;
        private int numFiles;
        private long totalSize;
        private List<Folder> children;

        public Folder(String name) {
            this.name = name;
            this.numFiles = 0;
            this.totalSize = 0;
            this.children = new ArrayList<>();
        }

        public void addChild(Folder child) {
            this.children.add(child);
        }

        public String getName() {
            return this.name;
        }

        public int getNumFiles() {
            return this.numFiles;
        }

        public long getTotalSize() {
            return this.totalSize;
        }

        public List<Folder> getChildren() {
            return this.children;
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter the path of the folder to scan: ");
			String path = scanner.nextLine();

			File rootFile = new File(path);
			Folder rootFolder = new Folder(rootFile.getName());
			populateFolder(rootFolder, rootFile);

			printFolder(rootFolder, 0);
		}
    }

    private static void populateFolder(Folder parentFolder, File parentFile) {
        if (parentFile.isDirectory()) {
            for (File file : parentFile.listFiles()) {
                if (file.isDirectory()) {
                    Folder childFolder = new Folder(file.getName());
                    populateFolder(childFolder, file);
                    parentFolder.addChild(childFolder);
                } else {
                    parentFolder.numFiles++;
                    parentFolder.totalSize += file.length();
                }
            }
        }
    }

    private static void printFolder(Folder folder, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.printf("%s (%d files, %d bytes)\n", folder.getName(), folder.getNumFiles(), folder.getTotalSize());
        for (Folder child : folder.getChildren()) {
            printFolder(child, depth + 1);
        }
    }
}