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

package schemacrawler.schema;


import java.io.Serializable;

/**
 * Represents a named object.
 *
 * @author Sualeh Fatehi
 */
public interface NamedObject
  extends Serializable, Comparable<NamedObject>
{

  /**
   * Getter for fully qualified name of object.
   *
   * @return Fully qualified of the object
   */
  String getFullName();

  /**
   * A value guaranteed to be unique in the database for this object.
   */
  String getLookupKey();

  /**
   * Getter for name of object.
   *
   * @return Name of the object
   */
  String getName();

}
