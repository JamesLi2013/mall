package com.james.mall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件处理
 */
public class CopyFile {
    public static List<String> mExcludeDir = new ArrayList<>();

    public static void main(String[] args) {

    }

    public static void copyDir(String beforeDir, String outDir) {
        addExcludeDir();
//		File file = new File("E:/openSource/LearnWord/");
//		String afterDir = "F:/after/";
        File file = new File(beforeDir);
        File afterFile = new File(outDir);
        if (!afterFile.exists()) {
            afterFile.mkdirs();
        } else {
            //待删除
        }
        copyDir(file, outDir);
    }

    private static void addExcludeDir() {
        mExcludeDir.add(".gradle");
//		mExcludeDir.add(".idea");
        mExcludeDir.add(".svn");
//		mExcludeDir.add("gradle");
        mExcludeDir.add("build");

        mExcludeDir.add("classes");
        mExcludeDir.add("upload-dir");
        mExcludeDir.add("target");
    }

    /**
     * 完整复制一个目录下所有文件到另外一个目录
     */
    private static void copyFiles(File file, String dir) {
        File[] fileArray = file.listFiles();
        if (fileArray != null) {
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    File dirFile = new File(dir, f.getName());
                    dirFile.mkdirs();
                    copyFiles(f, dirFile.getAbsolutePath());
                } else {
                    try {
                        File file3 = new File(dir, f.getName());
                        FileOutputStream fos = new FileOutputStream(file3);
                        FileInputStream fis = new FileInputStream(f);
                        byte bys[] = new byte[10240];
                        int len = -1;
                        while ((len = fis.read(bys)) != -1) {
                            fos.write(bys, 0, len);
                        }
                        fis.close();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 完整复制一个目录下所有文件到另外一个目录
     */
    private static void copyDir(File file, String dir) {
        File[] fileArray = file.listFiles();
        if (fileArray != null) {
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    if (!mExcludeDir.contains(f.getName())) {
                        File dirFile = new File(dir, f.getName());
                        dirFile.mkdirs();
                        copyDir(f, dirFile.getAbsolutePath());
                    }
                } else {
                    try {
                        String newName = f.getName();
                        if (f.getName().endsWith(".java")) {
//							newName = newName.substring(0, newName.length() - 5).concat(".txt");
                            newName = newName.concat(".txt");
                        }
                        File file3 = new File(dir, newName);
                        FileOutputStream fos = new FileOutputStream(file3);
                        FileInputStream fis = new FileInputStream(f);
                        byte bys[] = new byte[10240];
                        int len = -1;
                        while ((len = fis.read(bys)) != -1) {
                            fos.write(bys, 0, len);
                        }
                        fis.close();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * rename  some files
     */
    public static void renameDir(File file) {
        File[] fileArray = file.listFiles();
        if (fileArray != null) {
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    renameDir(f);
                } else {
                    if (f.getName().endsWith(".java.txt")) {
                        File file1 = new File(f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 4));
                        f.renameTo(file1);
                    }

                }
            }
        }
    }


    public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }

    public static void packExclude(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        if (path.toFile().getAbsolutePath().contains("classes") ||
                                path.toFile().getAbsolutePath().contains("target") ||
                                path.toFile().getAbsolutePath().contains("gradle") ||
                                path.toFile().getAbsolutePath().contains("build") ||
                                path.toFile().getAbsolutePath().contains("keystore") ||
                                path.toFile().getAbsolutePath().contains("libs") ||
                                path.toFile().getAbsolutePath().contains("jniLibs") ||
                                path.toFile().getAbsolutePath().contains("svn") ||
                                path.toFile().getAbsolutePath().contains("StoreGooglePlay") ||
                                path.toFile().getAbsolutePath().contains("StoreRaiskid") ||
                                path.toFile().getAbsolutePath().contains("upload-dir")) {

                        } else {
                            ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                            try {
                                zs.putNextEntry(zipEntry);
                                Files.copy(path, zs);
                                zs.closeEntry();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    });
        }
    }

    public static void packAll(String sourceDirPath, String zipFilePath) throws IOException {
        File file =new File(zipFilePath);
        if (file.exists()) file.delete();
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
        }
    }

}