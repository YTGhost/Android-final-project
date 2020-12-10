/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.today.step.lib;
public interface ISportStepInterface extends android.os.IInterface
{
  /** Default implementation for ISportStepInterface. */
  public static class Default implements com.today.step.lib.ISportStepInterface
  {
    /**
         * 获取当前时间运动步数
         */
    @Override public int getCurrentTimeSportStep() throws android.os.RemoteException
    {
      return 0;
    }
    /**
          * 获取所有步数列表，json格式，如果数据过多建议在线程中获取，否则会阻塞UI线程
          */
    @Override public java.lang.String getTodaySportStepArray() throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.today.step.lib.ISportStepInterface
  {
    private static final java.lang.String DESCRIPTOR = "com.today.step.lib.ISportStepInterface";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.today.step.lib.ISportStepInterface interface,
     * generating a proxy if needed.
     */
    public static com.today.step.lib.ISportStepInterface asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.today.step.lib.ISportStepInterface))) {
        return ((com.today.step.lib.ISportStepInterface)iin);
      }
      return new com.today.step.lib.ISportStepInterface.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_getCurrentTimeSportStep:
        {
          data.enforceInterface(descriptor);
          int _result = this.getCurrentTimeSportStep();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_getTodaySportStepArray:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getTodaySportStepArray();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.today.step.lib.ISportStepInterface
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
           * 获取当前时间运动步数
           */
      @Override public int getCurrentTimeSportStep() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getCurrentTimeSportStep, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getCurrentTimeSportStep();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
            * 获取所有步数列表，json格式，如果数据过多建议在线程中获取，否则会阻塞UI线程
            */
      @Override public java.lang.String getTodaySportStepArray() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getTodaySportStepArray, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getTodaySportStepArray();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static com.today.step.lib.ISportStepInterface sDefaultImpl;
    }
    static final int TRANSACTION_getCurrentTimeSportStep = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getTodaySportStepArray = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    public static boolean setDefaultImpl(com.today.step.lib.ISportStepInterface impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Stub.Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.today.step.lib.ISportStepInterface getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
       * 获取当前时间运动步数
       */
  public int getCurrentTimeSportStep() throws android.os.RemoteException;
  /**
        * 获取所有步数列表，json格式，如果数据过多建议在线程中获取，否则会阻塞UI线程
        */
  public java.lang.String getTodaySportStepArray() throws android.os.RemoteException;
}
