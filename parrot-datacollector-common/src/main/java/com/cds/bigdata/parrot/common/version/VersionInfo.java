package com.cds.bigdata.parrot.common.version;

import com.cds.bigdata.parrot.VersionAnnotation;

/**
 * Created by chendongsheng5 on 2017/2/8.
 */
public class VersionInfo {

  private static Package myPackage;
  private static VersionAnnotation version;

  static {
    myPackage = VersionAnnotation.class.getPackage();
    version = myPackage.getAnnotation(VersionAnnotation.class);
  }

  /**
   * Get the meta-data for the parrot package.
   * @return get parrot package
   */
  static Package getPackage() {
    return myPackage;
  }

  /**
   * Get the parrot version.
   * @return the parrot version string, eg. "1.1.0"
   */
  public static String getVersion() {
    return version != null ? version.version() : "Unknown";
  }

  /**
   * Get the subversion revision number for the root directory
   * @return the revision number, eg. "100755"
   */
  public static String getRevision() {
    if(version != null
        && version.revision() != null
        && !version.revision().isEmpty()){
      return version.revision();
    }
    return "Unknown";
  }

  /**
   * Get the branch on which this originated.
   * @return The branch name, e.g. "trunk" or "branches/branch-1.1"
   */
  public static String getBranch() {
    return version != null ? version.branch() : "Unknown";
  }

  /**
   * The date that parrot was compiled.
   * @return the compilation date in unix date format
   */
  public static String getDate() {
    return version != null ? version.date() : "Unknown";
  }

  /**
   * The user that compiled parrot.
   * @return the username of the user
   */
  public static String getUser() {
    return version != null ? version.user() : "Unknown";
  }

  /**
   * Get the subversion URL for the root parrot directory.
   */
  public static String getUrl() {
    return version != null ? version.url() : "Unknown";
  }

  /**
   * Get the checksum of the source files from which parrot was
   * built.
   **/
  public static String getSrcChecksum() {
    return version != null ? version.srcChecksum() : "Unknown";
  }

  /**
   * Returns the build version info which includes version,
   * revision, user, date and source checksum
   */
  public static String getBuildVersion(){
    return VersionInfo.getVersion() +
        " from " + VersionInfo.getRevision() +
        " by " + VersionInfo.getUser() +
        " on " + VersionInfo.getDate() +
        " source checksum " + VersionInfo.getSrcChecksum();
  }

  public static void main(String[] args) {
    System.out.println("parrot v" + getVersion());
    System.out.println("Revision: " + getRevision());
    System.out.println("Branch: " + getBranch());
    System.out.println("Compiled by " + getUser() + " on " + getDate());
    System.out.println("From source with checksum " + getSrcChecksum());
  }

}
