package com.example.andriod.miwok;

class Word {

    private static final int N0_Image_Provided = -1;
    private static final int NO_SOUND_PROVIDED = -1;


    private String mDefaultTranslation;

    private String mMiwokTranslation;

    private int mImageResourceId ;

    private int mAudioResourceId = NO_SOUND_PROVIDED;


    //@param default translation is the word the user is already familiar with
    //@param miwok translation is the language translation of the english word.


    Word(String mDefaultTranslation, String mMiwokTranslation, int mImageResourceId){
       this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId = mImageResourceId;
    }


    Word(String mDefaultTranslation, String mMiwokTranslation, int mImageResourceId, int mAudioResourceId){
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId = mImageResourceId;
        this.mAudioResourceId = mAudioResourceId;
    }


    String getDefaultTranslation(){
        return(mDefaultTranslation);
    }

    String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    int getmImageResourceId() {return mImageResourceId;}

    int getMAudioResourceId() {return mAudioResourceId;}



    boolean hasImage() {
        return mImageResourceId != N0_Image_Provided;
    }
    boolean hasSound() {return mAudioResourceId != NO_SOUND_PROVIDED;}






    /**
     * Returns the string representation of the {@link Word} object.
     */
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mAudioResourceId=" + mAudioResourceId +
                ", mImageResourceId=" + mImageResourceId +

                '}';
    }


}
