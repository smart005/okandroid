package com.cloud.basicfun.beans;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/9/3
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class InfoExifBean {
    /**
     * 压缩级别
     */
    private ValueBean Compression = null;
    /**
     * 时间
     */
    private ValueBean DateTime = null;
    /**
     * exiftag
     */
    private ValueBean ExifTag = null;
    /**
     * 文件大小
     */
    private ValueBean FileSize = null;
    /**
     * 文件格式
     */
    private ValueBean Format = null;
    /**
     * gps经度
     */
    private ValueBean GPSLatitude = null;
    /**
     * gsp参考经度
     */
    private ValueBean GPSLatitudeRef = null;
    /**
     * gps纬度
     */
    private ValueBean GPSLongitude = null;
    /**
     * gps参考纬度
     */
    private ValueBean GPSLongitudeRef = null;
    /**
     * gps map资料
     */
    private ValueBean GPSMapDatum = null;
    /**
     * gps标识
     */
    private ValueBean GPSTag = null;
    /**
     * gps版本id
     */
    private ValueBean GPSVersionID = null;
    /**
     * 图片高度
     */
    private ValueBean ImageHeight = null;
    /**
     * 图片宽度
     */
    private ValueBean ImageWidth = null;
    /**
     * gpeg interchange format
     */
    private ValueBean JPEGInterchangeFormat = null;
    /**
     * jpeg interchange format length
     */
    private ValueBean JPEGInterchangeFormatLength = null;
    /**
     * orientation
     */
    private ValueBean Orientation = null;
    /**
     * resolution unit
     */
    private ValueBean ResolutionUnit = null;
    /**
     * software
     */
    private ValueBean Software = null;
    /**
     * x resolution
     */
    private ValueBean XResolution = null;
    /**
     * y resolution
     */
    private ValueBean YResolution = null;

    /**
     * 获取压缩级别
     */
    public ValueBean getCompression() {
        if (Compression == null) {
            Compression = new ValueBean();
        }
        return Compression;
    }

    /**
     * 设置压缩级别
     *
     * @param Compression
     */
    public void setCompression(ValueBean Compression) {
        this.Compression = Compression;
    }

    /**
     * 获取时间
     */
    public ValueBean getDateTime() {
        if (DateTime == null) {
            DateTime = new ValueBean();
        }
        return DateTime;
    }

    /**
     * 设置时间
     *
     * @param DateTime
     */
    public void setDateTime(ValueBean DateTime) {
        this.DateTime = DateTime;
    }

    /**
     * 获取exiftag
     */
    public ValueBean getExifTag() {
        if (ExifTag == null) {
            ExifTag = new ValueBean();
        }
        return ExifTag;
    }

    /**
     * 设置exiftag
     *
     * @param ExifTag
     */
    public void setExifTag(ValueBean ExifTag) {
        this.ExifTag = ExifTag;
    }

    /**
     * 获取文件大小
     */
    public ValueBean getFileSize() {
        if (FileSize == null) {
            FileSize = new ValueBean();
        }
        return FileSize;
    }

    /**
     * 设置文件大小
     *
     * @param FileSize
     */
    public void setFileSize(ValueBean FileSize) {
        this.FileSize = FileSize;
    }

    /**
     * 获取文件格式
     */
    public ValueBean getFormat() {
        if (Format == null) {
            Format = new ValueBean();
        }
        return Format;
    }

    /**
     * 设置文件格式
     *
     * @param Format
     */
    public void setFormat(ValueBean Format) {
        this.Format = Format;
    }

    /**
     * 获取gps经度
     */
    public ValueBean getGPSLatitude() {
        if (GPSLatitude == null) {
            GPSLatitude = new ValueBean();
        }
        return GPSLatitude;
    }

    /**
     * 设置gps经度
     *
     * @param GPSLatitude
     */
    public void setGPSLatitude(ValueBean GPSLatitude) {
        this.GPSLatitude = GPSLatitude;
    }

    /**
     * 获取gsp参考经度
     */
    public ValueBean getGPSLatitudeRef() {
        if (GPSLatitudeRef == null) {
            GPSLatitudeRef = new ValueBean();
        }
        return GPSLatitudeRef;
    }

    /**
     * 设置gsp参考经度
     *
     * @param GPSLatitudeRef
     */
    public void setGPSLatitudeRef(ValueBean GPSLatitudeRef) {
        this.GPSLatitudeRef = GPSLatitudeRef;
    }

    /**
     * 获取gps纬度
     */
    public ValueBean getGPSLongitude() {
        if (GPSLongitude == null) {
            GPSLongitude = new ValueBean();
        }
        return GPSLongitude;
    }

    /**
     * 设置gps纬度
     *
     * @param GPSLongitude
     */
    public void setGPSLongitude(ValueBean GPSLongitude) {
        this.GPSLongitude = GPSLongitude;
    }

    /**
     * 获取gps参考纬度
     */
    public ValueBean getGPSLongitudeRef() {
        if (GPSLongitudeRef == null) {
            GPSLongitudeRef = new ValueBean();
        }
        return GPSLongitudeRef;
    }

    /**
     * 设置gps参考纬度
     *
     * @param GPSLongitudeRef
     */
    public void setGPSLongitudeRef(ValueBean GPSLongitudeRef) {
        this.GPSLongitudeRef = GPSLongitudeRef;
    }

    /**
     * 获取gps
     * map资料
     */
    public ValueBean getGPSMapDatum() {
        if (GPSMapDatum == null) {
            GPSMapDatum = new ValueBean();
        }
        return GPSMapDatum;
    }

    /**
     * 设置gps
     * map资料
     *
     * @param GPSMapDatum
     */
    public void setGPSMapDatum(ValueBean GPSMapDatum) {
        this.GPSMapDatum = GPSMapDatum;
    }

    /**
     * 获取gps标识
     */
    public ValueBean getGPSTag() {
        if (GPSTag == null) {
            GPSTag = new ValueBean();
        }
        return GPSTag;
    }

    /**
     * 设置gps标识
     *
     * @param GPSTag
     */
    public void setGPSTag(ValueBean GPSTag) {
        this.GPSTag = GPSTag;
    }

    /**
     * 获取gps版本id
     */
    public ValueBean getGPSVersionID() {
        if (GPSVersionID == null) {
            GPSVersionID = new ValueBean();
        }
        return GPSVersionID;
    }

    /**
     * 设置gps版本id
     *
     * @param GPSVersionID
     */
    public void setGPSVersionID(ValueBean GPSVersionID) {
        this.GPSVersionID = GPSVersionID;
    }

    /**
     * 获取图片高度
     */
    public ValueBean getImageHeight() {
        if (ImageHeight == null) {
            ImageHeight = new ValueBean();
        }
        return ImageHeight;
    }

    /**
     * 设置图片高度
     *
     * @param ImageHeight
     */
    public void setImageHeight(ValueBean ImageHeight) {
        this.ImageHeight = ImageHeight;
    }

    /**
     * 获取图片宽度
     */
    public ValueBean getImageWidth() {
        if (ImageWidth == null) {
            ImageWidth = new ValueBean();
        }
        return ImageWidth;
    }

    /**
     * 设置图片宽度
     *
     * @param ImageWidth
     */
    public void setImageWidth(ValueBean ImageWidth) {
        this.ImageWidth = ImageWidth;
    }

    /**
     * 获取gpeg
     * interchange
     * format
     */
    public ValueBean getJPEGInterchangeFormat() {
        if (JPEGInterchangeFormat == null) {
            JPEGInterchangeFormat = new ValueBean();
        }
        return JPEGInterchangeFormat;
    }

    /**
     * 设置gpeg
     * interchange
     * format
     *
     * @param JPEGInterchangeFormat
     */
    public void setJPEGInterchangeFormat(ValueBean JPEGInterchangeFormat) {
        this.JPEGInterchangeFormat = JPEGInterchangeFormat;
    }

    /**
     * 获取jpeg
     * interchange
     * format
     * length
     */
    public ValueBean getJPEGInterchangeFormatLength() {
        if (JPEGInterchangeFormatLength == null) {
            JPEGInterchangeFormatLength = new ValueBean();
        }
        return JPEGInterchangeFormatLength;
    }

    /**
     * 设置jpeg
     * interchange
     * format
     * length
     *
     * @param JPEGInterchangeFormatLength
     */
    public void setJPEGInterchangeFormatLength(ValueBean JPEGInterchangeFormatLength) {
        this.JPEGInterchangeFormatLength = JPEGInterchangeFormatLength;
    }

    /**
     * 获取orientation
     */
    public ValueBean getOrientation() {
        if (Orientation == null) {
            Orientation = new ValueBean();
        }
        return Orientation;
    }

    /**
     * 设置orientation
     *
     * @param Orientation
     */
    public void setOrientation(ValueBean Orientation) {
        this.Orientation = Orientation;
    }

    /**
     * 获取resolution
     * unit
     */
    public ValueBean getResolutionUnit() {
        if (ResolutionUnit == null) {
            ResolutionUnit = new ValueBean();
        }
        return ResolutionUnit;
    }

    /**
     * 设置resolution
     * unit
     *
     * @param ResolutionUnit
     */
    public void setResolutionUnit(ValueBean ResolutionUnit) {
        this.ResolutionUnit = ResolutionUnit;
    }

    /**
     * 获取software
     */
    public ValueBean getSoftware() {
        if (Software == null) {
            Software = new ValueBean();
        }
        return Software;
    }

    /**
     * 设置software
     *
     * @param Software
     */
    public void setSoftware(ValueBean Software) {
        this.Software = Software;
    }

    /**
     * 获取x
     * resolution
     */
    public ValueBean getXResolution() {
        if (XResolution == null) {
            XResolution = new ValueBean();
        }
        return XResolution;
    }

    /**
     * 设置x
     * resolution
     *
     * @param XResolution
     */
    public void setXResolution(ValueBean XResolution) {
        this.XResolution = XResolution;
    }

    /**
     * 获取y
     * resolution
     */
    public ValueBean getYResolution() {
        if (YResolution == null) {
            YResolution = new ValueBean();
        }
        return YResolution;
    }

    /**
     * 设置y
     * resolution
     *
     * @param YResolution
     */
    public void setYResolution(ValueBean YResolution) {
        this.YResolution = YResolution;
    }
}
