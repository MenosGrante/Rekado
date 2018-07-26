#include <cstring>
#include <thread>
#include <chrono>
#include <jni.h>
#include <linux/usb/ch9.h>
#include <linux/usbdevice_fs.h>
#include <sys/ioctl.h>
extern "C" JNIEXPORT void JNICALL
Java_com_pavelrekun_rekado_services_lakka_LakkaLoader_nativeControlReadUnbounded(JNIEnv *env,
                                                                                 jclass,
                                                                                 jint fd,
                                                                                 jint size) {


    char* buffer = new char[sizeof(usb_ctrlrequest) + size];
    usb_ctrlrequest* header = (usb_ctrlrequest*) buffer;
    header->bRequestType = USB_TYPE_STANDARD | USB_RECIP_ENDPOINT | USB_DIR_IN;
    header->bRequest = 0;
    header->wValue = 0;
    header->wIndex = 0;
    header->wLength = (__le16) size;
    memset(&buffer[sizeof(usb_ctrlrequest)], 0, (size_t) size);

    usbdevfs_urb* urb = (usbdevfs_urb*) new char[sizeof(usbdevfs_urb) + 1024];
    memset(urb, 0, sizeof(usbdevfs_urb) + 1024);
    urb->type = USBDEVFS_URB_TYPE_CONTROL;
    urb->endpoint = 0;
    urb->buffer = buffer;
    urb->buffer_length = sizeof(usb_ctrlrequest) + size;
    urb->usercontext = (void*) 0xf0f;


    ioctl(fd, USBDEVFS_SUBMITURB, urb);
    std::this_thread::sleep_for(std::chrono::milliseconds(100));
    ioctl(fd, USBDEVFS_DISCARDURB, urb);
    usbdevfs_urb* purb;
    do {
        if (ioctl(fd, USBDEVFS_REAPURB, &purb)) {
            break;
        }
    } while (purb != urb);

    delete[] urb;
    delete[] buffer;
}