/*
 *
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2015, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */
package schemacrawler.crawl;


import static sf.util.Utility.isBlank;

import java.io.Serializable;
import java.util.Arrays;

import schemacrawler.schema.Property;

abstract class AbstractProperty
  implements Property
{

  private static final long serialVersionUID = -7150431683440256142L;

  private final String name;
  private final Serializable value;

  AbstractProperty(final String name, final Serializable value)
  {
    if (isBlank(name))
    {
      throw new IllegalArgumentException("No property name provided");
    }
    this.name = name.trim();
    if (value != null && value.getClass().isArray())
    {
      this.value = (Serializable) Arrays.asList((Object[]) value);
    }
    else
    {
      this.value = value;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(final Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (!(obj instanceof AbstractProperty))
    {
      return false;
    }
    final AbstractProperty other = (AbstractProperty) obj;
    if (name == null)
    {
      if (other.name != null)
      {
        return false;
      }
    }
    else if (!name.equals(other.name))
    {
      return false;
    }
    if (value == null)
    {
      if (other.value != null)
      {
        return false;
      }
    }
    else if (!value.equals(other.value))
    {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getName()
  {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Serializable getValue()
  {
    return value;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + (name == null? 0: name.hashCode());
    result = prime * result + (value == null? 0: value.hashCode());
    return result;
  }

}
