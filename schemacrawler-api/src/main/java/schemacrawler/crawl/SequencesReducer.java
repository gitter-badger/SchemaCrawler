/*
 *
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
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


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import schemacrawler.filter.DatabaseObjectFilter;
import schemacrawler.filter.NamedObjectFilter;
import schemacrawler.filter.PassthroughFilter;
import schemacrawler.schema.Sequence;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;

class SequencesReducer
  implements Reducer<Sequence>
{

  private final NamedObjectFilter<Sequence> sequenceFilter;

  public SequencesReducer(final SchemaCrawlerOptions options)
  {
    if (options == null)
    {
      sequenceFilter = new PassthroughFilter<Sequence>();
    }
    else
    {
      sequenceFilter = new DatabaseObjectFilter<Sequence>(options,
                                                          options
                                                            .getSequenceInclusionRule());
    }
  }

  @Override
  public void reduce(final Collection<? extends Sequence> allSequences)
  {
    if (allSequences == null)
    {
      return;
    }
    allSequences.retainAll(doReduce(allSequences));
  }

  private Collection<Sequence> doReduce(final Collection<? extends Sequence> allSequences)
  {

    final Set<Sequence> reducedSequences = new HashSet<>();
    for (final Sequence sequence: allSequences)
    {
      if (sequenceFilter.test(sequence))
      {
        reducedSequences.add(sequence);
      }
    }

    return reducedSequences;
  }

}
