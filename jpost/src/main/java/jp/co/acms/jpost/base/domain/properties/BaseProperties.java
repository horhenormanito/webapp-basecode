
package jp.co.acms.jpost.base.domain.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class BaseProperties {

  @Autowired
  private Environment env;

  protected abstract String getFormId();

  protected abstract String getFormName();

  /**
   * Return whether the given property key is available for resolution, i.e. if
   * the value for the given key is not {@code null}.
   */
  public boolean containsKey(String key) {
    return env.containsProperty(mergeFormClassName(key));
  }

  /**
   * Return the property value associated with the given key, or {@code null} if
   * the key cannot be resolved.
   *
   * @param key the property name to resolve
   * @see #getValue(String, String)
   * @see #getValue(String, Class)
   * @see #getRequiredProperty(String)
   */
  public String getValue(String key) {
    return env.getProperty(mergeFormClassName(key));
  }

  /**
   * Return the property value associated with the given key, or
   * {@code defaultValue} if the key cannot be resolved.
   *
   * @param key          the property name to resolve
   * @param defaultValue the default value to return if no value is found
   * @see #getRequiredProperty(String)
   * @see #getValue(String, Class)
   */
  public String getValue(String key, String defaultValue) {
    return env.getProperty(mergeFormClassName(key), defaultValue);
  }

  /**
   * Return the property value associated with the given key, or {@code null} if
   * the key cannot be resolved.
   *
   * @param key        the property name to resolve
   * @param targetType the expected type of the property value
   * @see #getRequiredProperty(String, Class)
   */
  public <T> T getValue(String key, Class<T> targetType) {
    return env.getProperty(mergeFormClassName(key), targetType);
  }

  /**
   * Return the property value associated with the given key, or
   * {@code defaultValue} if the key cannot be resolved.
   *
   * @param key          the property name to resolve
   * @param targetType   the expected type of the property value
   * @param defaultValue the default value to return if no value is found
   * @see #getRequiredProperty(String, Class)
   */
  public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
    return env.getProperty(mergeFormClassName(key), targetType, defaultValue);
  }

  private String mergeFormClassName(String key) {
    if (!key.startsWith(getFormId())) {
      key = getFormId().concat("Form.").concat(key);
    }
    return key;
  }

}
