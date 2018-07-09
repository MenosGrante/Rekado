#include <jni.h>
#include <string>
#include <cstdlib>
#include <sys/ioctl.h>
#include <linux/usbdevice_fs.h>
#include <linux/usb/ch9.h>

extern "C" JNIEXPORT jint
JNICALL
Java_com_pavelrekun_rekado_services_payloads_PayloadLoader_nativeTriggerExploit(
        JNIEnv *env,
        jobject /* this */,
        jint fd,
        jint length) {

    static_assert(sizeof(struct usb_ctrlrequest) == 8);

    int buf_size = sizeof(struct usb_ctrlrequest) + length;
    void *buffer = calloc(1, buf_size);
    struct usbdevfs_urb *purb;

    struct usb_ctrlrequest *ctrl_req = (struct usb_ctrlrequest *) buffer;
    ctrl_req->bRequestType = USB_DIR_IN | USB_RECIP_INTERFACE;
    ctrl_req->bRequest = USB_REQ_GET_STATUS;
    ctrl_req->wLength = length;

    struct usbdevfs_urb urb = {
            .type = USBDEVFS_URB_TYPE_CONTROL,
            .endpoint = 0,
            .buffer = buffer,
            .buffer_length = buf_size,
            .usercontext = (void *) 0x1337,
    };

    if (ioctl(fd, USBDEVFS_SUBMITURB, &urb) < 0)
        return -1;

    if (ioctl(fd, USBDEVFS_DISCARDURB, &urb) < 0)
        return -2;

    if (ioctl(fd, USBDEVFS_REAPURB, &purb) < 0)
        return -3;

    if (purb->usercontext != (void *) 0x1337)
        return -4;

    free(buffer);
    return 0;
}