package com.github.badoualy.telegram.tl.api.request;

import com.github.badoualy.telegram.tl.TLContext;
import com.github.badoualy.telegram.tl.api.auth.TLSentCode;
import com.github.badoualy.telegram.tl.core.TLMethod;
import com.github.badoualy.telegram.tl.core.TLObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.github.badoualy.telegram.tl.StreamUtils.readInt;
import static com.github.badoualy.telegram.tl.StreamUtils.readTLBool;
import static com.github.badoualy.telegram.tl.StreamUtils.readTLObject;
import static com.github.badoualy.telegram.tl.StreamUtils.readTLString;
import static com.github.badoualy.telegram.tl.StreamUtils.writeBoolean;
import static com.github.badoualy.telegram.tl.StreamUtils.writeInt;
import static com.github.badoualy.telegram.tl.StreamUtils.writeString;
import static com.github.badoualy.telegram.tl.TLObjectUtils.SIZE_BOOLEAN;
import static com.github.badoualy.telegram.tl.TLObjectUtils.SIZE_CONSTRUCTOR_ID;
import static com.github.badoualy.telegram.tl.TLObjectUtils.SIZE_INT32;
import static com.github.badoualy.telegram.tl.TLObjectUtils.computeTLStringSerializedSize;

/**
 * @author Yannick Badoual yann.badoual@gmail.com
 * @see <a href="http://github.com/badoualy/kotlogram">http://github.com/badoualy/kotlogram</a>
 */
public class TLRequestAuthSendCode extends TLMethod<TLSentCode> {
    public static final int CONSTRUCTOR_ID = 0x86aef0ec;

    protected int flags;

    protected boolean allowFlashcall;

    protected String phoneNumber;

    protected Boolean currentNumber;

    protected int apiId;

    protected String apiHash;

    private final String _constructor = "auth.sendCode#86aef0ec";

    public TLRequestAuthSendCode() {
    }

    public TLRequestAuthSendCode(boolean allowFlashcall, String phoneNumber, Boolean currentNumber, int apiId, String apiHash) {
        this.allowFlashcall = allowFlashcall;
        this.phoneNumber = phoneNumber;
        this.currentNumber = currentNumber;
        this.apiId = apiId;
        this.apiHash = apiHash;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TLSentCode deserializeResponse(InputStream stream, TLContext context) throws IOException {
        final TLObject response = readTLObject(stream, context);
        if (response == null) {
            throw new IOException("Unable to parse response");
        }
        if (!(response instanceof TLSentCode)) {
            throw new IOException("Incorrect response type, expected getClass().getCanonicalName(), found response.getClass().getCanonicalName()");
        }
        return (TLSentCode) response;
    }

    private void computeFlags() {
        flags = 0;
        flags = currentNumber != null ? (flags | 1) : (flags & ~1);
        // Fields below are just utils boolean flags, they serve only when deserializing
        // The flag value at the given bit is computed above by a TLObject
        allowFlashcall = (flags & 1) != 0;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        computeFlags();

        writeInt(flags, stream);
        writeString(phoneNumber, stream);
        if ((flags & 1) != 0) {
            if (currentNumber == null) throwNullFieldException("currentNumber", flags);
            writeBoolean(currentNumber, stream);
        }
        writeInt(apiId, stream);
        writeString(apiHash, stream);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        flags = readInt(stream);
        allowFlashcall = (flags & 1) != 0;
        phoneNumber = readTLString(stream);
        currentNumber = (flags & 1) != 0 ? readTLBool(stream) : null;
        apiId = readInt(stream);
        apiHash = readTLString(stream);
    }

    @Override
    public int computeSerializedSize() {
        computeFlags();

        int size = SIZE_CONSTRUCTOR_ID;
        size += SIZE_INT32;
        size += computeTLStringSerializedSize(phoneNumber);
        if ((flags & 1) != 0) {
            if (currentNumber == null) throwNullFieldException("currentNumber", flags);
            size += SIZE_BOOLEAN;
        }
        size += SIZE_INT32;
        size += computeTLStringSerializedSize(apiHash);
        return size;
    }

    @Override
    public String toString() {
        return _constructor;
    }

    @Override
    public int getConstructorId() {
        return CONSTRUCTOR_ID;
    }

    public boolean getAllowFlashcall() {
        return allowFlashcall;
    }

    public void setAllowFlashcall(boolean allowFlashcall) {
        this.allowFlashcall = allowFlashcall;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Boolean currentNumber) {
        this.currentNumber = currentNumber;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getApiHash() {
        return apiHash;
    }

    public void setApiHash(String apiHash) {
        this.apiHash = apiHash;
    }
}
