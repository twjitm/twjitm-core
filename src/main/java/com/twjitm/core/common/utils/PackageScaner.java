package com.twjitm.core.common.utils;


import com.twjitm.core.common.netstack.entity.tcp.AbstractNettyNetProtoBufTcpMessage;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import sun.net.www.protocol.file.FileURLConnection;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageScaner {
    /**
     * Logger for this class
     */
    //  public static final Logger logger = Logger.g;
    public static String[] scanNamespaceFiles(String namespace, String fileext, boolean isReturnCanonicalPath) {
        return scanNamespaceFiles(namespace, fileext, isReturnCanonicalPath, false);
    }

    public static String[] scanNamespaceFiles(String namespace, String fileext, boolean isReturnCanonicalPath, boolean checkSub) {
        String respath = namespace.replace('.', '/');
        respath = respath.replace('.', '/');

        List<String> tmpNameList = new ArrayList<String>();
        try {
            URL url = null;
            //logger.info("url path" + respath);
            if (!respath.startsWith("/"))
                url = PackageScaner.class.getResource("/" + respath);
            else
                url = PackageScaner.class.getResource(respath);

            URLConnection tmpURLConnection = url.openConnection();
            String tmpItemName;
            if (tmpURLConnection instanceof JarURLConnection) {
                JarURLConnection tmpJarURLConnection = (JarURLConnection) tmpURLConnection;
                int tmpPos;
                String tmpPath;
                JarFile jarFile = tmpJarURLConnection.getJarFile();
                Enumeration<JarEntry> entrys = jarFile.entries();
                while (entrys.hasMoreElements()) {
                    JarEntry tmpJarEntry = entrys.nextElement();
                    if (!tmpJarEntry.isDirectory()) {
                        tmpItemName = tmpJarEntry.getName();
                        if (tmpItemName.indexOf('$') < 0
                                && (fileext == null || tmpItemName.endsWith(fileext))) {
                            tmpPos = tmpItemName.lastIndexOf('/');
                            if (tmpPos > 0) {
                                tmpPath = tmpItemName.substring(0, tmpPos);
                                if (checkSub) {
                                    if (tmpPath.startsWith(respath)) {

                                        String r = tmpItemName.substring(respath.length() + 1).replaceAll("/", ".");
                                        tmpNameList.add(r);
                                    }
                                } else {
                                    if (respath.equals(tmpPath)) {
                                        tmpNameList.add(tmpItemName.substring(tmpPos + 1));
                                    }
                                }

                            }
                        }
                    }
                }
                jarFile.close();
            } else if (tmpURLConnection instanceof FileURLConnection) {
                File file = new File(url.getFile());
                if (file.exists() && file.isDirectory()) {
                    File[] fileArray = file.listFiles();
                    for (File f : fileArray) {
                        if (f.isDirectory() && f.getName().indexOf(".") != -1)
                            continue;

                        if (isReturnCanonicalPath)
                            tmpItemName = f.getCanonicalPath();
                        else
                            tmpItemName = f.getName();
                        if (f.isDirectory()) {
                            String[] inner = scanNamespaceFiles(namespace + "." + tmpItemName, fileext, isReturnCanonicalPath);
                            if (inner == null) {
                                continue;
                            }
                            for (String i : inner) {
                                if (i != null) {
                                    tmpNameList.add(tmpItemName + "." + i);
                                }
                            }
                        } else if (fileext == null || tmpItemName.endsWith(fileext)) {
                            tmpNameList.add(tmpItemName);
                        } else {
                            continue;// 明确一下，不符合要求就跳过
                        }
                    }
                } else {
                    //  logger.error("scaning shutdown,invalid package path:" + url.getFile());
                }
            }
        } catch (Exception e) {
            // logger.error("scaning shutdown,invalid package path error" + e.toString());
        }


        if (tmpNameList.size() > 0) {
            String[] r = new String[tmpNameList.size()];
            tmpNameList.toArray(r);
            tmpNameList.clear();
            return r;
        }
        return null;
    }

    public static void main(String[] args) {
       /*String[] clzzs= scanNamespaceFiles("com.twjitm.common.logic",".class",false,true);
       for(int i=0;i<clzzs.length;i++){
           System.out.println(clzzs[i]);
       }*/
        List<Class> list = getSubClasses(AbstractNettyNetProtoBufTcpMessage.class, "com.twjitm.*.*");
        for (Class clazz : list) {
            System.out.println(clazz.getSimpleName());
        }
    }

    /**
     * get parent class to subclass
     *
     * @param parentClass 父类
     * @param packagePath 位置
     * @return 子类集和
     */
    public static List<Class> getSubClasses(final Class parentClass,
                                            final String packagePath) {
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                false);
        provider.addIncludeFilter(new AssignableTypeFilter(parentClass));
        final Set<BeanDefinition> components = provider
                .findCandidateComponents(packagePath);
        final List<Class> subClasses = new ArrayList<Class>();
        for (final BeanDefinition component : components) {
            @SuppressWarnings("unchecked") final Class cls;
            try {
                cls = Class.forName(component
                        .getBeanClassName());
                if (Modifier.isAbstract(cls.getModifiers())) {
                    continue;
                }
                subClasses.add(cls);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return subClasses;
    }


}
