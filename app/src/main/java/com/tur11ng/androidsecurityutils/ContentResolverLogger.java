package com.tur11ng.androidsecurityutils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Utility class for logging text content from various sources using ContentResolver.
 * Supports logging from URIs, InputStreams, and Intents with data URIs.
 */
public class ContentResolverLogger {

    private static String TAG = "ContentResolverLogger";
    private static final int MAX_CONTENT_LENGTH = 10000; // Limit for logged content
    private static final int MAX_LINES = 100; // Maximum number of lines to log

    /**
     * Logs content from a URI using the provided ContentResolver.
     *
     * @param tagSuffix suffix appended to the base TAG to help distinguish callers
     * @param context the Context to get the ContentResolver
     * @param uri the URI to read content from
     */
    public static void log(String tagSuffix, Context context, Uri uri) {
        TAG = TAG + ":" + tagSuffix;

        StringBuilder sb = new StringBuilder();
        sb.append("---- ContentResolver from URI ----\n");

        if (context == null) {
            sb.append("Context is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        if (uri == null) {
            sb.append("URI is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        sb.append("URI: ").append(uri.toString()).append("\n");
        sb.append("Scheme: ").append(uri.getScheme()).append("\n");
        sb.append("Authority: ").append(uri.getAuthority()).append("\n");
        sb.append("Path: ").append(uri.getPath()).append("\n");

        ContentResolver resolver = context.getContentResolver();

        try {
            // Try to get the MIME type
            String mimeType = resolver.getType(uri);
            sb.append("MIME Type: ").append(mimeType).append("\n");

            // Try to open and read the content
            InputStream inputStream = resolver.openInputStream(uri);
            if (inputStream != null) {
                sb.append("---- Content ----\n");
                String content = readInputStream(inputStream);
                sb.append(content);
                sb.append("\n---- End of Content ----\n");
                inputStream.close();
            } else {
                sb.append("InputStream is null - cannot read content\n");
            }

            // Try to query the URI for metadata
            sb.append("---- URI Metadata ----\n");
            logUriMetadata(sb, resolver, uri);

        } catch (SecurityException e) {
            sb.append("SecurityException: ").append(e.getMessage()).append("\n");
        } catch (IOException e) {
            sb.append("IOException: ").append(e.getMessage()).append("\n");
        } catch (Exception e) {
            sb.append("Exception: ").append(e.getClass().getSimpleName())
                    .append(" - ").append(e.getMessage()).append("\n");
        }

        sb.append("---- End of ContentResolver Log ----");
        Log.i(TAG, sb.toString());
    }

    /**
     * Logs content from an InputStream.
     *
     * @param tagSuffix suffix appended to the base TAG to help distinguish callers
     * @param inputStream the InputStream to read from
     */
    public static void log(String tagSuffix, InputStream inputStream) {
        TAG = TAG + ":" + tagSuffix;

        StringBuilder sb = new StringBuilder();
        sb.append("---- ContentResolver from InputStream ----\n");

        if (inputStream == null) {
            sb.append("InputStream is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        try {
            sb.append("InputStream Class: ").append(inputStream.getClass().getName()).append("\n");
            sb.append("Available bytes: ").append(inputStream.available()).append("\n");
            sb.append("---- Content ----\n");
            String content = readInputStream(inputStream);
            sb.append(content);
            sb.append("\n---- End of Content ----\n");
        } catch (IOException e) {
            sb.append("IOException: ").append(e.getMessage()).append("\n");
        } catch (Exception e) {
            sb.append("Exception: ").append(e.getClass().getSimpleName())
                    .append(" - ").append(e.getMessage()).append("\n");
        }

        sb.append("---- End of ContentResolver Log ----");
        Log.i(TAG, sb.toString());
    }

    /**
     * Logs content from an Intent's data URI using ContentResolver.
     *
     * @param tagSuffix suffix appended to the base TAG to help distinguish callers
     * @param context the Context to get the ContentResolver
     * @param intent the Intent containing the data URI
     */
    public static void log(String tagSuffix, Context context, Intent intent) {
        TAG = TAG + ":" + tagSuffix;

        StringBuilder sb = new StringBuilder();
        sb.append("---- ContentResolver from Intent ----\n");

        if (context == null) {
            sb.append("Context is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        if (intent == null) {
            sb.append("Intent is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        // Log basic Intent info
        sb.append("Intent Action: ").append(intent.getAction()).append("\n");
        sb.append("Intent Type: ").append(intent.getType()).append("\n");

        Uri uri = intent.getData();
        if (uri == null) {
            sb.append("Intent Data URI is null - checking ClipData\n");

            // Check if there's a ClipData with URI
            if (intent.getClipData() != null && intent.getClipData().getItemCount() > 0) {
                uri = intent.getClipData().getItemAt(0).getUri();
                if (uri != null) {
                    sb.append("Found URI in ClipData\n");
                }
            }
        }

        if (uri == null) {
            sb.append("No URI found in Intent\n");
            Log.i(TAG, sb.toString());
            return;
        }

        sb.append("URI: ").append(uri.toString()).append("\n");
        sb.append("Scheme: ").append(uri.getScheme()).append("\n");
        sb.append("Authority: ").append(uri.getAuthority()).append("\n");
        sb.append("Path: ").append(uri.getPath()).append("\n");

        ContentResolver resolver = context.getContentResolver();

        try {
            // Try to get the MIME type
            String mimeType = resolver.getType(uri);
            sb.append("MIME Type: ").append(mimeType).append("\n");

            // Try to open and read the content
            InputStream inputStream = resolver.openInputStream(uri);
            if (inputStream != null) {
                sb.append("---- Content ----\n");
                String content = readInputStream(inputStream);
                sb.append(content);
                sb.append("\n---- End of Content ----\n");
                inputStream.close();
            } else {
                sb.append("InputStream is null - cannot read content\n");
            }

            // Try to query the URI for metadata
            sb.append("---- URI Metadata ----\n");
            logUriMetadata(sb, resolver, uri);

        } catch (SecurityException e) {
            sb.append("SecurityException: ").append(e.getMessage()).append("\n");
        } catch (IOException e) {
            sb.append("IOException: ").append(e.getMessage()).append("\n");
        } catch (Exception e) {
            sb.append("Exception: ").append(e.getClass().getSimpleName())
                    .append(" - ").append(e.getMessage()).append("\n");
        }

        sb.append("---- End of ContentResolver Log ----");
        Log.i(TAG, sb.toString());
    }

    /**
     * Logs content and metadata from a URI with a ContentResolver.
     *
     * @param tagSuffix suffix appended to the base TAG to help distinguish callers
     * @param resolver the ContentResolver to use
     * @param uri the URI to read content from
     */
    public static void log(String tagSuffix, ContentResolver resolver, Uri uri) {
        TAG = TAG + ":" + tagSuffix;

        StringBuilder sb = new StringBuilder();
        sb.append("---- ContentResolver from URI ----\n");

        if (resolver == null) {
            sb.append("ContentResolver is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        if (uri == null) {
            sb.append("URI is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        sb.append("URI: ").append(uri.toString()).append("\n");
        sb.append("Scheme: ").append(uri.getScheme()).append("\n");
        sb.append("Authority: ").append(uri.getAuthority()).append("\n");
        sb.append("Path: ").append(uri.getPath()).append("\n");

        try {
            // Try to get the MIME type
            String mimeType = resolver.getType(uri);
            sb.append("MIME Type: ").append(mimeType).append("\n");

            // Try to open and read the content
            InputStream inputStream = resolver.openInputStream(uri);
            if (inputStream != null) {
                sb.append("---- Content ----\n");
                String content = readInputStream(inputStream);
                sb.append(content);
                sb.append("\n---- End of Content ----\n");
                inputStream.close();
            } else {
                sb.append("InputStream is null - cannot read content\n");
            }

            // Try to query the URI for metadata
            sb.append("---- URI Metadata ----\n");
            logUriMetadata(sb, resolver, uri);

        } catch (SecurityException e) {
            sb.append("SecurityException: ").append(e.getMessage()).append("\n");
        } catch (IOException e) {
            sb.append("IOException: ").append(e.getMessage()).append("\n");
        } catch (Exception e) {
            sb.append("Exception: ").append(e.getClass().getSimpleName())
                    .append(" - ").append(e.getMessage()).append("\n");
        }

        sb.append("---- End of ContentResolver Log ----");
        Log.i(TAG, sb.toString());
    }

    /**
     * Reads content from an InputStream and returns it as a String.
     * Limits the content to MAX_CONTENT_LENGTH characters and MAX_LINES lines.
     */
    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int lineCount = 0;
        int totalChars = 0;

        while ((line = reader.readLine()) != null && lineCount < MAX_LINES) {
            if (totalChars + line.length() > MAX_CONTENT_LENGTH) {
                int remainingChars = MAX_CONTENT_LENGTH - totalChars;
                if (remainingChars > 0) {
                    content.append(line, 0, remainingChars);
                }
                content.append("\n... (content truncated - exceeded max length) ...\n");
                break;
            }
            content.append(line).append("\n");
            totalChars += line.length() + 1; // +1 for newline
            lineCount++;
        }

        if (lineCount >= MAX_LINES) {
            content.append("... (content truncated - exceeded max lines) ...\n");
        }

        return content.toString();
    }

    /**
     * Queries the URI for metadata and logs column names and values.
     */
    private static void logUriMetadata(StringBuilder sb, ContentResolver resolver, Uri uri) {
        Cursor cursor = null;
        try {
            cursor = resolver.query(uri, null, null, null, null);
            if (cursor != null) {
                sb.append("Cursor row count: ").append(cursor.getCount()).append("\n");
                sb.append("Cursor column count: ").append(cursor.getColumnCount()).append("\n");

                if (cursor.getColumnCount() > 0) {
                    sb.append("Columns:\n");
                    String[] columnNames = cursor.getColumnNames();
                    for (String columnName : columnNames) {
                        sb.append("  - ").append(columnName).append("\n");
                    }

                    // Log first row of data if available
                    if (cursor.moveToFirst()) {
                        sb.append("First row data:\n");
                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            String columnName = cursor.getColumnName(i);
                            String value = cursor.getString(i);
                            sb.append("  ")
                                    .append(columnName)
                                    .append(" = ")
                                    .append(value)
                                    .append("\n");
                        }
                    }
                }
            } else {
                sb.append("Query returned null cursor\n");
            }
        } catch (Exception e) {
            sb.append("Error querying URI: ")
                    .append(e.getClass().getSimpleName())
                    .append(" - ")
                    .append(e.getMessage())
                    .append("\n");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

