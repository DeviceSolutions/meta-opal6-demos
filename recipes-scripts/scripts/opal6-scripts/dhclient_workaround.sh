#!/bin/bash

if [ ! -f "/sbin/dhclient-real" ]
then
        mv /sbin/dhclient /sbin/dhclient-real
        echo '#!/bin/bash' > /sbin/dhclient
        echo 'dhclient-real -nw $@' >> /sbin/dhclient
        chmod a+x /sbin/dhclient
		sync
fi
