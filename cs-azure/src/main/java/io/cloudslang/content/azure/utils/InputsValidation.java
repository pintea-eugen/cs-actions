package io.cloudslang.content.azure.utils;

import io.cloudslang.content.utils.StringUtilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static io.cloudslang.content.azure.utils.AuthorizationInputNames.CLIENT_ID;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.EXPIRY;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.IDENTIFIER;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.PASSWORD;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.PRIMARY_OR_SECONDARY_KEY;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.PROXY_PORT;
import static io.cloudslang.content.azure.utils.AuthorizationInputNames.USERNAME;
import static io.cloudslang.content.azure.utils.Constants.EXCEPTION_INVALID_PROXY;
import static io.cloudslang.content.azure.utils.Constants.EXCEPTION_NULL_EMPTY;
import static io.cloudslang.content.azure.utils.StorageInputNames.BLOB_NAME;
import static io.cloudslang.content.azure.utils.StorageInputNames.CONTAINER_NAME;
import static io.cloudslang.content.utils.OtherUtilities.isValidIpPort;

/**
 * Created by victor on 28.09.2016.
 */
public final class InputsValidation {
    @NotNull
    public static List<String> verifyStorageInputs(@Nullable final String accountName, @Nullable final String key, @Nullable final String proxyPort) {
        final List<String> exceptionMessages = new ArrayList<>();
        addVerifyNotNullOrEmpty(exceptionMessages, accountName, IDENTIFIER);
        addVerifyNotNullOrEmpty(exceptionMessages, key, PRIMARY_OR_SECONDARY_KEY);
        addVerifyProxy(exceptionMessages, proxyPort, PROXY_PORT);
        return exceptionMessages;
    }

    @NotNull
    public static List<String> verifyStorageInputs(@Nullable final String accountName, @Nullable final String key, @Nullable final String containerName, @Nullable final String proxyPort) {
        final List<String> exceptionMessages = verifyStorageInputs(accountName, key, proxyPort);
        addVerifyNotNullOrEmpty(exceptionMessages, containerName, CONTAINER_NAME);
        return exceptionMessages;
    }

    @NotNull
    public static List<String> verifyStorageInputs(@Nullable final String accountName, @Nullable final String key, @Nullable final String containerName, @Nullable final String proxyPort, @Nullable final String blobName) {
        final List<String> exceptionMessages = verifyStorageInputs(accountName, key, containerName, proxyPort);
        addVerifyNotNullOrEmpty(exceptionMessages, blobName, BLOB_NAME);
        return exceptionMessages;
    }

    @NotNull
    public static List<String> verifySharedAccessInputs(@Nullable final String identifier, @Nullable final String primaryOrSecondaryKey, @Nullable final String expiry) {
        final List<String> exceptionMessages = new ArrayList<>();
        addVerifyNotNullOrEmpty(exceptionMessages, identifier, IDENTIFIER);
        addVerifyNotNullOrEmpty(exceptionMessages, primaryOrSecondaryKey, PRIMARY_OR_SECONDARY_KEY);
        addVerifyNotNullOrEmpty(exceptionMessages, expiry, EXPIRY);
        return exceptionMessages;
    }

    @NotNull
    public static List<String> verifyAuthorizationInputs(@Nullable final String username, @Nullable final String password, @Nullable final String clientId, @NotNull final String proxyPort) {
        final List<String> exceptionMessages = new ArrayList<>();
        addVerifyNotNullOrEmpty(exceptionMessages, username, USERNAME);
        addVerifyNotNullOrEmpty(exceptionMessages, password, PASSWORD);
        addVerifyNotNullOrEmpty(exceptionMessages, clientId, CLIENT_ID);
        addVerifyProxy(exceptionMessages, proxyPort, PROXY_PORT);
        return exceptionMessages;
    }

    @NotNull
    public static List<String> verifyProxyPortInput(@Nullable final String proxyPort) {
        final List<String> exceptionMessages = new ArrayList<>();
        addVerifyProxy(exceptionMessages, proxyPort, PROXY_PORT);
        return exceptionMessages;
    }

    @NotNull
    private static List<String> addVerifyNotNullOrEmpty(@NotNull List<String> exceptions, @Nullable final String input, @NotNull final String inputName) {
        if (StringUtilities.isEmpty(input)) {
            exceptions.add(String.format(EXCEPTION_NULL_EMPTY, inputName));
        }
        return exceptions;
    }

    @NotNull
    private static List<String> addVerifyProxy(@NotNull List<String> exceptions, @Nullable final String input, @NotNull final String inputName) {
        if (StringUtilities.isEmpty(input)) {
            exceptions.add(String.format(EXCEPTION_NULL_EMPTY, inputName));
        } else if (!isValidIpPort(input)) {
            exceptions.add(String.format(EXCEPTION_INVALID_PROXY, PROXY_PORT));
        }
        return exceptions;
    }
}
