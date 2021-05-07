/*
 *
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2020
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 *
 */
package org.test.resteasyjackson;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.column.page.PageReadStore;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.convert.GroupRecordConverter;
import org.apache.parquet.format.converter.ParquetMetadataConverter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.metadata.BlockMetaData;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.ColumnIOFactory;
import org.apache.parquet.io.MessageColumnIO;
import org.apache.parquet.io.RecordReader;
import org.apache.parquet.schema.MessageType;

/**
 * Reads parquet file records from all row groups as strings.
 */
public class ParquetRecordReader {

    private final Configuration config;
    private final Path path;

    public ParquetRecordReader(final Configuration config, final Path path) {
        this.config = config;
        this.path = path;
    }

    /**
     * Create a stream of records converted to string.
     * This stream may throw runtime exceptions while it is reading records,
     * therefore is should be initialized inside a try with resources block.
     *
     * @return the stream of records as stream
     * @throws java.io.IOException throw if the parquet file reader can't be created.
     */
    public Stream<String> stream() throws IOException {
        final Reader reader = new Reader();
        final Spliterator<String> spliterator = Spliterators.spliterator(reader, Long.MAX_VALUE,
            Spliterator.NONNULL | Spliterator.IMMUTABLE);
        return StreamSupport.stream(spliterator, false).onClose(reader::close);
    }

    /**
     * The parquet file reader exposing an {@link java.util.Iterator} interface.
     */
    private class Reader implements Iterator<String>, Closeable {

        private final MessageType schema;
        private final ParquetFileReader reader;
        private final Iterator<BlockMetaData> rowGroupIterator;
        private PageReadStore pages;
        private final MessageColumnIO columnIO;
        private RecordReader<Group> recordReader;
        private long rowGroupSize;
        private long currentRow;

        Reader() throws IOException {
            reader = ParquetFileReader.open(HadoopInputFile.fromPath(path, config));
            schema = reader.getFileMetaData().getSchema();;
            columnIO = new ColumnIOFactory().getColumnIO(schema);
            rowGroupIterator = reader.getRowGroups().iterator();
        }

        @Override
        public boolean hasNext() {
            return isNextRowNotInRowGroup() ? rowGroupIterator.hasNext() : rowGroupSize > 0 && isNextRowInRowGroup();
        }

        private boolean isNextRowNotInRowGroup() {
            return !isNextRowInRowGroup();
        }

        private boolean isNextRowInRowGroup() {
            return currentRow + 1 < rowGroupSize;
        }

        /**
         * Gets the next parquet record as a string.
         *
         * @return the parquet record
         */
        @Override
        public String next() {
            if (isCurrentRowNotInRowGroup()) {
                iterateToNextRowGroup();
            } else {
                currentRow++;
            }
            return recordReader.read().toString();
        }

        private boolean isCurrentRowNotInRowGroup() {
            return currentRow >= rowGroupSize;
        }

        private void iterateToNextRowGroup() {
            try {
                if (null != (pages = reader.readNextRowGroup())) {
                    rowGroupIterator.next();
                    recordReader = columnIO.getRecordReader(pages, new GroupRecordConverter(schema));
                    rowGroupSize = pages.getRowCount();
                    currentRow = 0;
                }
            } catch (IOException e) {
                throw new ParquetRowGroupRetrievalException(e);
            }
        }

        @Override
        public void close() {
            try {
                reader.close();
            } catch (IOException e) {
                throw new ParquetReaderCloseException(e);
            }
        }
    }

    private static class ParquetRowGroupRetrievalException extends RuntimeException {
        ParquetRowGroupRetrievalException(final Throwable cause) {
            super(cause);
        }
    }

    private static class ParquetReaderCloseException extends RuntimeException {
        ParquetReaderCloseException(final Throwable cause) {
            super(cause);
        }
    }
}
