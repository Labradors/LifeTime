package org.jiangtao.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by erdaye on 2015/12/1.
 */
public class NoteFilter implements FilenameFilter{
    @Override
    public boolean accept(File dir, String filename) {
        return (filename.endsWith(".txt"));
    }
}
