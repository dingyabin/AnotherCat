package com.dingyabin.work.common.generator.processor;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.plugins.EqualsHashCodePlugin;
import org.mybatis.generator.plugins.SerializablePlugin;
import org.mybatis.generator.plugins.ToStringPlugin;

/**
 * @author 丁亚宾
 * Date: 2021/8/29.
 * Time:23:04
 */
public class PluginGeneratorProcessor extends PluginConfiguration implements ConfigXmlProcessor {


    private static final String PLUGIN_TYPE = "        <plugin type= \"%s\"></plugin>    \n";


    private boolean needToString;

    private boolean needSerializable;

    private boolean needEqualsHashCode;

    public PluginGeneratorProcessor(boolean needToString, boolean needSerializable, boolean needEqualsHashCode) {
        this.needToString = needToString;
        this.needSerializable = needSerializable;
        this.needEqualsHashCode = needEqualsHashCode;
    }


    @Override
    public String process(String xmlString) {
        StringBuilder stringBuilder = new StringBuilder(StringUtils.EMPTY);
        if (needToString) {
            stringBuilder.append("<!-- 重写toString方法 -->\n");
            stringBuilder.append(String.format(PLUGIN_TYPE, ToStringPlugin.class.getName()));
        }
        if (needSerializable) {
            stringBuilder.append("<!-- 实现Serializable接口 -->\n");
            stringBuilder.append(String.format(PLUGIN_TYPE, SerializablePlugin.class.getName()));
        }
        if (needEqualsHashCode) {
            stringBuilder.append("<!-- 重写equals 和 hashCode方法 -->\n");
            stringBuilder.append(String.format(PLUGIN_TYPE, EqualsHashCodePlugin.class.getName()));
        }
        xmlString = xmlString.replace("${context.plugin}", stringBuilder.toString());
        return xmlString;
    }




    public boolean isNeedToString() {
        return needToString;
    }

    public PluginGeneratorProcessor needToString() {
        this.needToString = true;
        return this;
    }

    public boolean isNeedSerializable() {
        return needSerializable;
    }

    public PluginGeneratorProcessor needSerializable() {
        this.needSerializable = true;
        return this;
    }

    public boolean isNeedEqualsHashCode() {
        return needEqualsHashCode;
    }

    public PluginGeneratorProcessor needEqualsHashCode() {
        this.needEqualsHashCode = true;
        return this;
    }
}
