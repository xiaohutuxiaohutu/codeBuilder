package com.augurit.agcloud.helper.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * PropertiesManager
 * 
 * <P>
 * 属性文件管理器
 * </P>
 * 
 * @author 高康 2014-5-26
 * @version 0.0.1
 */
public final class PropertiesManager
{
	// ----------------------------------------------------- Properties

	/**
	 * Log4J日志
	 */
	private final static Log log = LogFactory.getLog( PropertiesManager.class );

	/**
	 * 配置文件对象
	 */
	private final Properties properties;

	// ----------------------------------------------------- Constructors

	/**
	 * 由文件路径读取配置文件
	 * 
	 * @param filePath 文件路径
	 * 
	 * @throws FileNotFoundException 文件找不到异常
	 */
	public PropertiesManager( String filePath ) throws FileNotFoundException
	{
		this( new FileInputStream( filePath ), false );
	}

	/**
	 * 由输入流读取配置文件
	 * 
	 * @param in
	 */
	public PropertiesManager( InputStream in )
	{
		this( in, false );
	}

	/**
	 * 由输入流读取配置文件
	 * 
	 * @param in 文件输入流
	 * @param isSecurity 是否支持加密
	 */
	public PropertiesManager( InputStream in, Boolean isSecurity )
	{
		properties = new Properties();

		try
		{
			properties.load( in );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			
			if ( log.isErrorEnabled() )
			{
				log.error( "PropertiesManager load a properties from a input stream failed:" + e.getMessage(), e );
			}

			throw new IllegalStateException( "PropertiesManager load a properties from a input stream failed" );
		}
	}

	// ----------------------------------------------------- Methods

	/**
	 * 读取对象
	 * 
	 * @param key 对象的关键字
	 * 
	 * @return 对象
	 */
	public Object getObject( String key )
	{
		return properties.get( key );
	}

	/**
	 * 读取字符串
	 * 
	 * @param key 对象的关键字
	 * 
	 * @return 字符串
	 */
	public String getString( String key )
	{
		return (String) getObject( key );
	}

	/**
	 * 读取布尔值
	 * 
	 * @param key 对象的关键字
	 * 
	 * @return 布尔值
	 */
	public Boolean getBoolean( String key )
	{
		return Boolean.valueOf( getString( key ) );
	}

	/**
	 * 读取Int
	 * 
	 * @param key 对象的关键字
	 * 
	 * @return 数字
	 */
	public Integer getInt( String key )
	{
		return Integer.parseInt( getString( key ) );
	}

	/**
	 * 获取配置文件Map
	 * 
	 * @return Map对象
	 */
	@SuppressWarnings("unchecked")
	public Map getPropertyMap()
	{
		return properties;
	}

	/**
	 * 获取配置属性文件
	 * 
	 * @return 配置属性文件
	 */
	public Properties getProperties()
	{
		return properties;
	}

	/**
	 * 设置属性值
	 * 
	 * @param key 关键字
	 * 
	 * @param value 值
	 */
	public void setProperty( String key, String value )
	{
		properties.setProperty( key, value );
	}

	/**
	 * 保存配置文件
	 * 
	 * @param filePath 文件路径
	 */
	public void saveProperty( String filePath )
	{
		synchronized ( this )
		{
			try
			{
				File file = new File( filePath );
				properties.store( new FileOutputStream( filePath ), file.getName() );
			}
			catch ( Exception e )
			{
				if ( log.isErrorEnabled() )
				{
					log.error( "PropertiesManager save file failed:", e );
				}

				throw new IllegalStateException( "PropertiesManager save file failed" );
			}
		}
	}
}
